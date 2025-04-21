package com.ecommerce.application.dto

import java.math.BigDecimal

data class PointCommand(
    val userId: Long,
    val price: BigDecimal
)
