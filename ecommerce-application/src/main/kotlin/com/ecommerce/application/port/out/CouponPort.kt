package com.ecommerce.application.port.out

import com.ecommerce.domain.coupon.Coupon
import com.ecommerce.domain.coupon.UserCoupon

interface CouponPort {

    fun findCouponById(couponId: Long): Coupon

    fun commandCoupon(coupon: Coupon): Coupon

    fun findUserCouponBy(couponId: Long, userId: Long): UserCoupon

    fun isExistsUserCoupon(couponId: Long, userId: Long): Boolean

    fun commandUserCoupon(userCoupon: UserCoupon): UserCoupon

}