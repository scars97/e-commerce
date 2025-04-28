package com.ecommerce.application.service

import com.ecommerce.application.dto.OrderCommand
import com.ecommerce.application.port.`in`.OrderUseCase
import com.ecommerce.application.port.out.CouponPort
import com.ecommerce.application.port.out.ItemPort
import com.ecommerce.application.port.out.OrderPort
import com.ecommerce.domain.order.Order
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderPort: OrderPort
    private val itemPort: ItemPort,
    private val couponPort: CouponPort
): OrderUseCase {

    @Transactional
    override fun placeOrder(orderCommand: OrderCommand): Order {
        // 객체 변환
        val order = orderCommand.toOrder()
        val orderItems = order.orderItems

        // 재고 차감
        val items = itemPort.getItemsIn(orderItems.map { it.itemId }.toList())
        val quantityOfItem = orderItems.associate { it.itemId to it.quantity }
        items.forEach {
            it.isSelling()
            it.deductStock(quantityOfItem[it.id])
        }
        itemPort.updateItems(items)

        // 주문 금액 계산
        order.calculateOriginPrice(items)

        // 쿠폰 적용
        if (order.couponId != null) {
            val userCoupon = couponPort.findUserCouponBy(order.couponId!!, order.userId)
            couponPort.use(userCoupon.use())

            order.calculateDiscountPrice(userCoupon.coupon)
        }
        
        // 주문 저장
        return orderPort.commandOrder(order)
    }

}