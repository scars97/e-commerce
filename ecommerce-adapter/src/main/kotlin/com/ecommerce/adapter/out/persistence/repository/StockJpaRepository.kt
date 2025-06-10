package com.ecommerce.adapter.out.persistence.repository

import com.ecommerce.adapter.out.persistence.entity.StockEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface StockJpaRepository: JpaRepository<StockEntity, Long> {

    fun findByItemId(itemId: Long): Optional<StockEntity>

}