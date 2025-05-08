package com.ecommerce.application.dto

import com.ecommerce.domain.payment.Payment
import java.math.BigDecimal

data class PaymentCommand(
    val userId: Long,
    val orderId: Long,
    val price: BigDecimal
) {

    fun toPayment(): Payment {
        return Payment(null, this.orderId, this.userId, this.price)
    }

}