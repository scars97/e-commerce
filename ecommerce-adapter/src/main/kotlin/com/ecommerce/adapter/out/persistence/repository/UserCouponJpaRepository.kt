package com.ecommerce.adapter.out.persistence.repository

import com.ecommerce.adapter.out.persistence.entity.UserCouponEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserCouponJpaRepository: JpaRepository<UserCouponEntity, Long> {

    fun findByCoupon_IdAndUserId(couponId: Long, userId: Long): Optional<UserCouponEntity>

    fun existsByCoupon_IdAndUserId(couponId: Long, userId: Long): Boolean

}