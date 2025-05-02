package com.ecommerce.domain.coupon

import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import java.time.LocalDateTime

class UserCoupon(
    val id: Long?,
    val userId: Long,
    val coupon: Coupon,
    var status: UserCouponStatus,
    val issuedAt: LocalDateTime,
    val expireAt: LocalDateTime,
    var usedAt: LocalDateTime?
) {

    enum class UserCouponStatus {
        USED, AVAILABLE, EXPIRED
    }

    companion object {
        fun register(userId: Long, coupon: Coupon): UserCoupon {
            return UserCoupon(
                id = null,
                userId = userId,
                coupon = coupon,
                status = UserCouponStatus.AVAILABLE,
                issuedAt = LocalDateTime.now(),
                expireAt = LocalDateTime.now().plusDays(coupon.expirationDay.toLong()),
                usedAt = null
            )
        }
    }

    fun validate() {
        if (this.status != UserCouponStatus.AVAILABLE) {
            throw CustomException(ErrorCode.INVALID_COUPON)
        }
        if (this.expireAt.isBefore(LocalDateTime.now())) {
            throw CustomException(ErrorCode.INVALID_COUPON)
        }
    }

    fun use(): UserCoupon {
        this.validate()

        this.status = UserCouponStatus.USED
        this.usedAt = LocalDateTime.now()
        return this
    }

}