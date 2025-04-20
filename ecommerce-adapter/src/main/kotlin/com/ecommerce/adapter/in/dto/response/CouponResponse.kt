package com.ecommerce.adapter.`in`.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDateTime

data class CouponResponse(
    val couponId: Long,
    val title: String,
    val type: String,
    val disCount: BigDecimal,
    val expirationDay: Int,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val issuedAt: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val expireAt: LocalDateTime
)
