package com.ecommerce.application.service

import com.ecommerce.application.dto.PaymentCommand
import com.ecommerce.domain.event.SendOrderInfoEvent
import com.ecommerce.application.port.`in`.PaymentUseCase
import com.ecommerce.application.port.out.OrderPort
import com.ecommerce.application.port.out.PaymentPort
import com.ecommerce.application.port.out.PointHistoryPort
import com.ecommerce.application.port.out.UserPort
import com.ecommerce.domain.Payment
import com.ecommerce.domain.PointHistory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PaymentService(
    private val paymentPort: PaymentPort,
    private val userPort: UserPort,
    private val pointHistoryPort: PointHistoryPort,
    private val orderPort: OrderPort,
    private val eventPublisher: ApplicationEventPublisher
): PaymentUseCase {

    @Transactional
    override fun pay(paymentCommand: PaymentCommand): Payment {
        val payment = paymentCommand.toPayment()

        // 사용자, 주문 정보 검증
        val user = userPort.findUserById(payment.userId)
        val order = orderPort.findOrderById(payment.orderId)
        payment.validatePrice(order.totalPrice)
        
        // 포인트 사용
        userPort.updateUser(user.pointUse(payment.price))

        // 포인트 내역 저장
        pointHistoryPort.saveHistory(
            PointHistory.createAtUsed(user.id, payment.price)
        )

        // 주문 상태 변경 -> PAID
        orderPort.commandOrder(order.paid())

        // 주문 정보 -> 데이터 플랫폼 전달
        eventPublisher.publishEvent(SendOrderInfoEvent(order))

        // 결제 생성
        return paymentPort.pay(payment)
    }

}