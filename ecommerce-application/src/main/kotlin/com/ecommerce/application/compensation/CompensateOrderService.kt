package com.ecommerce.application.compensation

import com.ecommerce.application.port.out.CouponPort
import com.ecommerce.application.port.out.OrderPort
import com.ecommerce.domain.order.Order
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CompensateOrderService(
    private val orderPort: OrderPort,
    private val couponPort: CouponPort
) {

    @Transactional
    fun compensateOrder(order: Order) {
        // 1. 쿠폰 사용 복구
        order.couponId?.let {
            couponPort.rollbackUserCoupon(it, order.userId)
        }

        // 2. 주문 상태 취소 처리
        order.cancel()
        orderPort.commandOrder(order)
    }

}