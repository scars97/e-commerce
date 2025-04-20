package com.ecommerce.adapter.`in`.dto.response

data class PopularItemResponse(
    val rank: Int,
    val cumulativeSales: Long,
    val item: ItemResponse
)