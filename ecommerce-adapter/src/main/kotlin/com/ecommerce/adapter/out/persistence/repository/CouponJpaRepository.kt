package com.ecommerce.adapter.out.persistence.repository

import com.ecommerce.adapter.out.persistence.entity.CouponEntity
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface CouponJpaRepository: JpaRepository<CouponEntity, Long> {

    @Query("select c from CouponEntity c where c.id = :couponId")
    fun findByIdWithLock(@Param("couponId") couponId: Long): Optional<CouponEntity>

}