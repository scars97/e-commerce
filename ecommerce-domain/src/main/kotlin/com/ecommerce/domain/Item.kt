package com.ecommerce.domain

import java.math.BigDecimal
import java.time.LocalDateTime

class Item(
    val id: Long,
    val categoryId: Long,
    val name: String,
    val price: BigDecimal,
    val thumbnail: String,
    val status: ItemStatus,
    val createAt: LocalDateTime,
    val modifiedAt: LocalDateTime
) {

    enum class ItemStatus {
        SELLING, SOLD_OUT
    }

}