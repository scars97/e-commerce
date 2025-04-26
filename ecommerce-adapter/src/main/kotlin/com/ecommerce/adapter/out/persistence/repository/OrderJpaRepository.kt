package com.ecommerce.adapter.out.persistence.repository

import com.ecommerce.adapter.out.persistence.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OrderJpaRepository: JpaRepository<OrderEntity, Long>