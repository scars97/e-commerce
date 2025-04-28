package com.ecommerce.domain

import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import java.math.BigDecimal

class Payment(
    val id: Long,
    val orderId: Long,
    val userId: Long,
    val price: BigDecimal,
    var status: PaymentStatus = PaymentStatus.PAID
) {

    enum class PaymentStatus {
        PAID, CANCEL
    }

    constructor(orderId: Long, userId: Long, price: BigDecimal): this(0, orderId, userId, price)

    fun validatePrice(totalPrice: BigDecimal) {
        if (this.price != totalPrice) {
            throw CustomException(ErrorCode.INVALID_PRICE)
        }
    }

}