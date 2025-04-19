package com.ecommerce.adapter.`in`.dto.response

import java.math.BigDecimal

data class PaymentResponse(
    val paymentId: Long,
    val orderId: Long,
    val userId: Long,
    val price: BigDecimal,
    val status: String
)