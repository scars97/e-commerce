package com.ecommerce.domain.item

import java.time.LocalDateTime

class Stock(
    val id: Long,
    var quantity: Long,
    val createAt: LocalDateTime,
    val modifiedAt: LocalDateTime
) {

    fun deduct(count: Long?): Long {
        if (this.quantity < count!!) {
            throw IllegalStateException("재고 부족")
        }

        this.quantity -= count

        return this.quantity
    }

}