package com.ecommerce.application.port.`in`

import com.ecommerce.application.dto.CouponCommand
import com.ecommerce.domain.coupon.Coupon

interface CouponUseCase {

    fun issued(couponCommand: CouponCommand): Coupon

}