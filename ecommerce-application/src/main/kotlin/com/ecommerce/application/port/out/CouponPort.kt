package com.ecommerce.application.port.out

import com.ecommerce.domain.coupon.Coupon
import com.ecommerce.domain.coupon.UserCoupon

interface CouponPort {

    fun findCouponById(couponId: Long): Coupon

    fun commandCoupon(coupon: Coupon): Coupon

    fun findUserCouponBy(userCouponId: Long, userId: Long): UserCoupon

    fun commandUserCoupon(userCoupon: UserCoupon): UserCoupon

}