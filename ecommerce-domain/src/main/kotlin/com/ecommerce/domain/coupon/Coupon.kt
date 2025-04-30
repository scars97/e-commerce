package com.ecommerce.domain.coupon

import java.math.BigDecimal

class Coupon(
    val id: Long,
    val title: String,
    val type: DiscountType,
    val discount: Long,
    val expirationDay: Int
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

}