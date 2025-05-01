package com.ecommerce.adapter.`in`.dto.response

import com.ecommerce.domain.Payment
import java.math.BigDecimal

data class PaymentResponse(
    val paymentId: Long,
    val orderId: Long,
    val userId: Long,
    val price: BigDecimal,
    val status: String
) {

    companion object {
        fun of(payment: Payment): PaymentResponse {
            return PaymentResponse(
                paymentId = payment.id,
                orderId = payment.orderId,
                userId = payment.userId,
                price = payment.price,
                status = payment.status.name
            )
        }
    }

}