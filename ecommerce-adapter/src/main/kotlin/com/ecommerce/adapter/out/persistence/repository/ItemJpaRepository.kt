package com.ecommerce.adapter.out.persistence.repository

import com.ecommerce.adapter.out.persistence.entity.ItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ItemJpaRepository: JpaRepository<ItemEntity, Long> {

    @Query("select i from ItemEntity i " +
            "join fetch i.stock s " +
            "where i.id in :itemIds")
    fun findAllById(@Param("itemIds") itemIds: List<Long>): List<ItemEntity>

}