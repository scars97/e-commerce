package com.ecommerce.adapter.out.persistence.core

import com.ecommerce.adapter.out.persistence.mapper.CouponMapper
import com.ecommerce.adapter.out.persistence.repository.UserCouponJpaRepository
import com.ecommerce.application.port.out.CouponPort
import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import com.ecommerce.domain.coupon.UserCoupon
import org.springframework.stereotype.Repository

@Repository
class CouponPersistenceAdapter(
    private val userCouponJpaRepository: UserCouponJpaRepository
): CouponPort {

    override fun findUserCouponBy(userCouponId: Long, userId: Long): UserCoupon {
        val userCoupon = userCouponJpaRepository.findByCouponIdAndUserId(userCouponId, userId)
            .orElseThrow { CustomException(ErrorCode.COUPON_NOT_FOUND) }

        return CouponMapper.toUserCoupon(userCoupon!!)
    }

    override fun use(userCoupon: UserCoupon) {
        val userCouponEntity = CouponMapper.toUserCouponEntity(userCoupon)

        userCouponJpaRepository.save(userCouponEntity)
    }

}