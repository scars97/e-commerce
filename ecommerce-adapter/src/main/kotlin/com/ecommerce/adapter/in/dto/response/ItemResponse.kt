package com.ecommerce.adapter.`in`.dto.response

import java.math.BigDecimal

data class ItemResponse(
    val itemId: Long,
    val name: String,
    val price: BigDecimal,
    val thumbnail: String,
    val status: String
)
