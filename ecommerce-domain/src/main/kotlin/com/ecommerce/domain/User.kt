package com.ecommerce.domain

import java.math.BigDecimal
import java.time.LocalDateTime

class User(
    val id: Long,
    val username: String,
    var point: BigDecimal,
    val createAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
) {

    fun pointRecharge(price: BigDecimal): User {
        this.point += price
        return this
    }

}