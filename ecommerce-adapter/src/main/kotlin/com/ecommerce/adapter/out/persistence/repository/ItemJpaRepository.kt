package com.ecommerce.adapter.out.persistence.repository

import com.ecommerce.adapter.out.persistence.entity.ItemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ItemJpaRepository: JpaRepository<ItemEntity, Long>