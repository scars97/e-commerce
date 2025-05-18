package com.ecommerce.adapter.out.persistence.repository

import com.ecommerce.adapter.out.persistence.entity.PointHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PointHistoryJpaRepository: JpaRepository<PointHistoryEntity, Long> {
}