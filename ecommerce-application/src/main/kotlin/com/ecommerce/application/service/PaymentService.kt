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

        val user = userPort.findUserById(payment.userId)
        val order = orderPort.findOrderById(payment.orderId)
        payment.priceEqualTo(order.totalPrice)
        
        userPort.updateUser(user.pointUse(payment.price))

        pointHistoryPort.saveHistory(
            PointHistory.createAtUsed(user.id, payment.price)
        )

        orderPort.commandOrder(order.paid())

        eventPublisher.publishEvent(SendOrderInfoEvent(order))

        return paymentPort.savePayment(payment)
    }

}