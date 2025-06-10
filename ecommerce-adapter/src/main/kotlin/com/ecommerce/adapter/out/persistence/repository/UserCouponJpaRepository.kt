package com.ecommerce.adapter.out.persistence.repository

import com.ecommerce.adapter.out.persistence.entity.UserCouponEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface UserCouponJpaRepository: JpaRepository<UserCouponEntity, Long> {

    fun findByCoupon_IdAndUserId(couponId: Long, userId: Long): Optional<UserCouponEntity>

    fun existsByCoupon_IdAndUserId(couponId: Long, userId: Long): Boolean

    @Modifying
    @Query("update UserCouponEntity uc " +
            "set uc.status = 'AVAILABLE' " +
            "where uc.coupon.id = :couponId and uc.userId = :userId and uc.status = 'USED'")
    fun rollbackToAvailable(
        @Param("couponId") couponId: Long,
        @Param("userId") userId: Long
    )

}