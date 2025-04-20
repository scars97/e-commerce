package com.ecommerce.adapter.`in`.dto.response

import java.math.BigDecimal

data class OrderResponse(
    val orderId: Long,
    val userId: Long,
    val couponId: Long?,
    val disCount: BigDecimal,
    val totalPrice: BigDecimal,
    val status: String
)