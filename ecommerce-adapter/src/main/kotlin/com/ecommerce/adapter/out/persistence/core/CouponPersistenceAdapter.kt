package com.ecommerce.adapter.out.persistence.core

import com.ecommerce.adapter.out.persistence.entity.QCouponEntity
import com.ecommerce.adapter.out.persistence.mapper.CouponMapper
import com.ecommerce.adapter.out.persistence.repository.CouponJpaRepository
import com.ecommerce.adapter.out.persistence.repository.UserCouponJpaRepository
import com.ecommerce.application.port.out.CouponPort
import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import com.ecommerce.domain.coupon.Coupon
import com.ecommerce.domain.coupon.UserCoupon
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class CouponPersistenceAdapter(
    private val couponMapper: CouponMapper,
    private val queryFactory: JPAQueryFactory,
    private val couponJpaRepository: CouponJpaRepository,
    private val userCouponJpaRepository: UserCouponJpaRepository
): CouponPort {

    override fun findCouponById(couponId: Long): Coupon {
        val coupon = couponJpaRepository.findByIdWithLock(couponId)
            .orElseThrow { CustomException(ErrorCode.COUPON_NOT_FOUND) }

        return couponMapper.toCoupon(coupon)
    }

    override fun commandCoupon(coupon: Coupon): Coupon {
        val entity = couponMapper.toCouponEntity(coupon)

        val saveCoupon = couponJpaRepository.save(entity)

        return couponMapper.toCoupon(saveCoupon)
    }

    override fun findUserCouponBy(couponId: Long, userId: Long): UserCoupon {
        val userCoupon = userCouponJpaRepository.findByCoupon_IdAndUserId(couponId, userId)
            .orElseThrow { CustomException(ErrorCode.COUPON_NOT_FOUND) }

        return couponMapper.toUserCoupon(userCoupon!!)
    }

    override fun isExistsUserCoupon(couponId: Long, userId: Long): Boolean {
        return userCouponJpaRepository.existsByCoupon_IdAndUserId(couponId, userId)
    }

    override fun commandUserCoupon(userCoupon: UserCoupon): UserCoupon {
        val userCouponEntity = couponMapper.toUserCouponEntity(userCoupon)

        val saveUserCoupon = userCouponJpaRepository.save(userCouponEntity)

        return couponMapper.toUserCoupon(saveUserCoupon)
    }

}