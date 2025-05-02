package com.ecommerce.adapter.`in`.dto.response

import com.ecommerce.domain.coupon.UserCoupon
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class CouponResponse(
    val couponId: Long,
    val title: String,
    val type: String,
    val discount: Long,
    val expirationDay: Int,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val issuedAt: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val expireAt: LocalDateTime
) {

    companion object {
        fun of(userCoupon: UserCoupon): CouponResponse {
            return CouponResponse(
                couponId = userCoupon.id!!,
                title = userCoupon.coupon.title,
                type = userCoupon.coupon.type.name,
                discount = userCoupon.coupon.discount,
                expirationDay = userCoupon.coupon.expirationDay,
                issuedAt = userCoupon.issuedAt,
                expireAt = userCoupon.expireAt
            )
        }
    }

}
