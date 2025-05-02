package com.ecommerce.application.service

import com.ecommerce.application.dto.CouponCommand
import com.ecommerce.application.port.`in`.CouponUseCase
import com.ecommerce.application.port.out.CouponPort
import com.ecommerce.application.port.out.UserPort
import com.ecommerce.domain.coupon.UserCoupon
import org.springframework.stereotype.Service

@Service
class CouponService(
    private val couponPort: CouponPort,
    private val userPort: UserPort
): CouponUseCase {

    override fun issued(couponCommand: CouponCommand): UserCoupon {
        // 사용자, 쿠폰 검증
        val coupon = couponPort.findCouponById(couponCommand.couponId)
        val user = userPort.findUserById(couponCommand.userId)

        // 쿠폰 재고 차감
        couponPort.commandCoupon(coupon.deduct())

        // 발급 쿠폰 저장
        return couponPort.commandUserCoupon(
            UserCoupon.register(user.id, coupon)
        )
    }

}