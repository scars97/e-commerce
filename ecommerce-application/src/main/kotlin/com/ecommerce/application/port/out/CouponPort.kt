package com.ecommerce.application.port.out

import com.ecommerce.domain.coupon.UserCoupon

interface CouponPort {

    fun findUserCouponBy(userCouponId: Long, userId: Long): UserCoupon

    fun use(userCoupon: UserCoupon)

}