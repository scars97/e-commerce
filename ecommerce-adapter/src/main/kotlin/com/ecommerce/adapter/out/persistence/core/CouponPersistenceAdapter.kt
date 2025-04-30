package com.ecommerce.adapter.out.persistence.core

import com.ecommerce.adapter.out.persistence.mapper.CouponMapper
import com.ecommerce.adapter.out.persistence.repository.CouponJpaRepository
import com.ecommerce.adapter.out.persistence.repository.UserCouponJpaRepository
import com.ecommerce.application.port.out.CouponPort
import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import com.ecommerce.domain.coupon.Coupon
import com.ecommerce.domain.coupon.UserCoupon
import org.springframework.stereotype.Repository

@Repository
class CouponPersistenceAdapter(
    private val couponJpaRepository: CouponJpaRepository,
    private val userCouponJpaRepository: UserCouponJpaRepository
): CouponPort {

    override fun findCouponById(couponId: Long): Coupon {
        val coupon = couponJpaRepository.findById(couponId)
            .orElseThrow { CustomException(ErrorCode.COUPON_NOT_FOUND) }

        return CouponMapper.toCoupon(coupon)
    }

    override fun commandCoupon(coupon: Coupon): Coupon {
        val entity = CouponMapper.toCouponEntity(coupon)

        val saveCoupon = couponJpaRepository.save(entity)

        return CouponMapper.toCoupon(saveCoupon)
    }

    override fun findUserCouponBy(userCouponId: Long, userId: Long): UserCoupon {
        val userCoupon = userCouponJpaRepository.findByCouponIdAndUserId(userCouponId, userId)
            .orElseThrow { CustomException(ErrorCode.COUPON_NOT_FOUND) }

        return CouponMapper.toUserCoupon(userCoupon!!)
    }

    override fun commandUserCoupon(userCoupon: UserCoupon) {
        val userCouponEntity = CouponMapper.toUserCouponEntity(userCoupon)

        userCouponJpaRepository.save(userCouponEntity)
    }
}