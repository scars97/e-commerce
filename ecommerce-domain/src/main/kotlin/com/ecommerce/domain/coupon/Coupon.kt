package com.ecommerce.domain.coupon

import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import java.math.BigDecimal

class Coupon(
    val id: Long?,
    val title: String,
    val type: DiscountType,
    val discount: Long,
    val expirationDay: Int,
    var quantity: Long
) {

    enum class DiscountType {
        RATE {
            override fun calculateDiscount(price: BigDecimal, discount: Long): BigDecimal {
                return price * (BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100)))
            }
        },
        AMOUNT {
            override fun calculateDiscount(price: BigDecimal, discount: Long): BigDecimal {
                return BigDecimal.valueOf(discount)
            }
        };

        abstract fun calculateDiscount(price: BigDecimal, discount: Long): BigDecimal
    }

    fun calculateDiscount(price: BigDecimal): BigDecimal {
        return this.type.calculateDiscount(price, this.discount)
    }

    fun deduct(): Coupon {
        if (this.quantity - 1L < 0) {
            throw CustomException(ErrorCode.COUPONS_ARE_EXHAUSTED)
        }

        this.quantity -= 1L
        return this
    }

}