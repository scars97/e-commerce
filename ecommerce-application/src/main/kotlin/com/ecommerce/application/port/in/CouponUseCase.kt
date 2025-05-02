package com.ecommerce.application.port.`in`

import com.ecommerce.application.dto.CouponCommand
import com.ecommerce.domain.coupon.UserCoupon

interface CouponUseCase {

    fun issued(couponCommand: CouponCommand): UserCoupon

}