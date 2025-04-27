package com.ecommerce.domain.item

import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode

class Stock(
    val id: Long,
    var quantity: Long
) {

    fun deduct(count: Long?): Long {
        if (this.quantity < count!!) {
            throw CustomException(ErrorCode.OUT_OF_STOCK)
        }

        this.quantity -= count

        return this.quantity
    }

}