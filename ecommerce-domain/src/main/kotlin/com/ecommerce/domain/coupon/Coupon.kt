package com.ecommerce.domain.coupon

import java.math.BigDecimal

class Coupon(
    val id: Long,
    val title: String,
    val type: CouponType,
    val discount: Long,
    val expirationDay: Int
) {

    enum class CouponType {
        RATE, AMOUNT
    }

    fun calculateDiscount(price: BigDecimal): BigDecimal {
        return when(this.type) {
            CouponType.RATE -> price * (BigDecimal.valueOf(this.discount).divide(BigDecimal.valueOf(100)))
            CouponType.AMOUNT -> BigDecimal.valueOf(this.discount)
        }
    }

}