package com.ecommerce.adapter.fixture

import com.ecommerce.adapter.out.persistence.entity.CouponEntity
import com.ecommerce.adapter.out.persistence.entity.UserCouponEntity
import com.ecommerce.adapter.out.persistence.repository.CouponJpaRepository
import com.ecommerce.adapter.out.persistence.repository.UserCouponJpaRepository
import com.ecommerce.domain.coupon.Coupon
import com.ecommerce.domain.coupon.UserCoupon
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CouponFixture(
    private val couponRepository: CouponJpaRepository,
    private val userCouponRepository: UserCouponJpaRepository
) {

    private lateinit var coupon: CouponEntity
    private lateinit var userCoupon: UserCouponEntity

    fun createCoupon(couponType: Coupon.DiscountType, discount: Long, quantity: Long): CouponEntity {
        coupon = couponRepository.save(CouponEntity(null, "선착순 쿠폰", couponType, discount, 30, quantity))
        return coupon
    }

    fun createAvailableUserCoupon(userId: Long, coupon: CouponEntity): UserCouponEntity {
        userCoupon = userCouponRepository.save(
            UserCouponEntity(null, userId, coupon, UserCoupon.UserCouponStatus.AVAILABLE, LocalDateTime.now(), LocalDateTime.now().plusDays(30), null)
        )
        return userCoupon
    }

    fun getCoupon(): CouponEntity {
        return this.coupon
    }

    fun getUserCoupon(): UserCouponEntity {
        return this.userCoupon
    }

}