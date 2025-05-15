package com.ecommerce.adapter.out.persistence.repository

import com.ecommerce.adapter.out.persistence.entity.StockEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface StockJpaRepository: JpaRepository<StockEntity, Long> {

    @Query("select s from StockEntity s where s.id = :id")
    fun findByIdWithLock(@Param("id") id: Long): Optional<StockEntity>

}