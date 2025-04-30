package com.ecommerce.adapter.out.persistence.repository

import com.ecommerce.adapter.out.persistence.entity.OrderItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface OrderItemJpaRepository: JpaRepository<OrderItemEntity, Long> {

    fun findByCreateAtGreaterThanEqual(period: LocalDateTime): List<OrderItemEntity>

}