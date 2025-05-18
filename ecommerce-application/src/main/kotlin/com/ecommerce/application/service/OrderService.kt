package com.ecommerce.application.service

import com.ecommerce.application.dto.OrderCommand
import com.ecommerce.application.port.`in`.OrderUseCase
import com.ecommerce.application.port.out.CouponPort
import com.ecommerce.application.port.out.ItemPort
import com.ecommerce.application.port.out.OrderPort
import com.ecommerce.domain.event.DeductStockEvent
import com.ecommerce.domain.order.Order
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderPort: OrderPort,
    private val itemPort: ItemPort,
    private val couponPort: CouponPort,
    private val eventPublisher: ApplicationEventPublisher
): OrderUseCase {

    @Transactional
    override fun placeOrder(orderCommand: OrderCommand): Order {
        val order = orderCommand.toOrder()
        val orderItems = order.orderItems

        val items = itemPort.getItemsIn(orderItems.map { it.itemId })
        items.forEach { it.isSelling() }

        order.calculateOriginPrice(items)

        order.couponId?.let {
            val userCoupon = couponPort.findUserCouponBy(order.couponId!!, order.userId)
            couponPort.commandUserCoupon(userCoupon.use())

            order.calculateDiscountPrice(userCoupon.coupon)
        }

        eventPublisher.publishEvent(DeductStockEvent(items, orderItems))
        
        return orderPort.commandOrder(order)
    }

}