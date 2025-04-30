package com.ecommerce.adapter.out.persistence.repository

import com.ecommerce.adapter.out.persistence.entity.CouponEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CouponJpaRepository: JpaRepository<CouponEntity, Long>