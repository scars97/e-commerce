package com.ecommerce.adapter.out.persistence.repository

import com.ecommerce.adapter.out.persistence.entity.UserCouponEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface UserCouponJpaRepository: JpaRepository<UserCouponEntity, Long> {

    @Query("select uc from UserCouponEntity uc " +
            "join fetch uc.coupon " +
            "where uc.id = :userCouponId and uc.userId = :userId")
    fun findByCouponIdAndUserId(
        @Param("userCouponId") userCouponId: Long,
        @Param("userId") userId: Long
    ): Optional<UserCouponEntity>

}