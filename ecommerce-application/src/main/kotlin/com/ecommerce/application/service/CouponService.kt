package com.ecommerce.application.service

import com.ecommerce.application.dto.CouponCommand
import com.ecommerce.application.port.`in`.CouponUseCase
import com.ecommerce.application.port.out.CouponPort
import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import com.ecommerce.common.lock.DistributeLockExecutor
import com.ecommerce.domain.coupon.UserCoupon
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class CouponService(
    private val couponPort: CouponPort,
    private val lockExecutor: DistributeLockExecutor
): CouponUseCase {

    override fun issued(couponCommand: CouponCommand): UserCoupon {
        return lockExecutor.lockWithTransaction(
            "issuedCoupon-${couponCommand.couponId}",
            5L,
            3L
        ) {
            // 사용자, 쿠폰 검증
            val coupon = couponPort.findCouponById(couponCommand.couponId)

            // 쿠폰 재고 차감
            couponPort.commandCoupon(coupon.deduct())

            try {
                couponPort.commandUserCoupon(UserCoupon.register(couponCommand.userId, coupon))
            } catch (e: DataIntegrityViolationException) {
                // 유니크 제약 위반 → 중복 발급
                throw CustomException(ErrorCode.COUPON_ALREADY_ISSUED)
            }
        }
    }

}