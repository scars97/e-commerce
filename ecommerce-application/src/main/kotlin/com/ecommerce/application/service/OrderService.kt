package com.ecommerce.application.service

import com.ecommerce.application.dto.OrderCommand
import com.ecommerce.application.port.`in`.OrderUseCase
import com.ecommerce.application.port.out.CouponPort
import com.ecommerce.application.port.out.ItemPort
import com.ecommerce.application.port.out.OrderPort
import com.ecommerce.common.lock.DistributeLockExecutor
import com.ecommerce.domain.event.DeductStockEvent
import com.ecommerce.domain.order.Order
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderPort: OrderPort,
    private val itemPort: ItemPort,
    private val couponPort: CouponPort,
    private val eventPublisher: ApplicationEventPublisher,
    private val lockExecutor: DistributeLockExecutor
): OrderUseCase {

    override fun placeOrder(orderCommand: OrderCommand): Order {
        val order = orderCommand.toOrder()
        val orderItems = order.orderItems

        val items = itemPort.getItemsIn(orderItems.map { it.itemId })
        items.forEach { it.isSelling() }

        // 재고 차감 로직 이벤트 처리
        val itemOfId = items.associateBy { it.id }
        orderItems.forEach {
            lockExecutor.lockWithTransaction("deductStock-${it.itemId}", 5L, 3L) {
                eventPublisher.publishEvent(DeductStockEvent(it, itemOfId[it.itemId]!!))
            }
        }

        order.calculateOriginPrice(items)

        order.couponId?.let {
            applyCouponTo(order)
        }

        return orderPort.commandOrder(order)
    }

    private fun applyCouponTo(order: Order) {
        val userCoupon = couponPort.findUserCouponBy(order.couponId!!, order.userId)
        couponPort.commandUserCoupon(userCoupon.use())

        order.calculateDiscountPrice(userCoupon.coupon)
    }

}