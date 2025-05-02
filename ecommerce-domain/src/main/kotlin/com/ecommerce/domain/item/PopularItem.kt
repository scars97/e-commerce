package com.ecommerce.domain.item

class PopularItem(
    val rank: Int,
    val cumulativeSales: Long,
    val item: Item
)