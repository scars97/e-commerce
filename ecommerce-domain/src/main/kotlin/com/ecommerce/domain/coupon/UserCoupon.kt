package com.ecommerce.domain.coupon

import java.time.LocalDateTime

class UserCoupon(
    val id: Long?,
    val userId: Long,
    val coupon: Coupon,
    var status: UserCouponStatus,
    val expireAt: LocalDateTime,
    var usedAt: LocalDateTime?
) {

    enum class UserCouponStatus {
        USED, AVAILABLE, EXPIRED
    }

    fun validate() {
        if (this.status != UserCouponStatus.AVAILABLE) {
            throw IllegalStateException("사용할 수 없는 쿠폰")
        }
        if (this.expireAt.isBefore(LocalDateTime.now())) {
            throw IllegalStateException("만료된 쿠폰")
        }
    }

    fun use(): UserCoupon {
        this.validate()

        this.status = UserCouponStatus.USED
        this.usedAt = LocalDateTime.now()
        return this
    }

}