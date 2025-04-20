package com.ecommerce.adapter.`in`.dto.response

import java.math.BigDecimal

data class PointResponse(
    val userId: Long,
    val remainingPoint: BigDecimal
)
