package com.ecommerce.domain

import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import java.math.BigDecimal
import java.time.LocalDateTime

class User(
    val id: Long?,
    val username: String,
    var point: BigDecimal = BigDecimal.ZERO,
    val createAt: LocalDateTime?,
    val modifiedAt: LocalDateTime?,
) {

    fun pointRecharge(price: BigDecimal): User {
        this.point += price
        return this
    }

    fun pointUse(price: BigDecimal): User {
        if (this.point < price) {
            throw CustomException(ErrorCode.INSUFFICIENT_POINT)
        }

        this.point -= price
        return this
    }

}