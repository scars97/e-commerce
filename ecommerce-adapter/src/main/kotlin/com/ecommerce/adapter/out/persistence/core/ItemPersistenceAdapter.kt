package com.ecommerce.adapter.out.persistence.core

import com.ecommerce.adapter.out.persistence.mapper.ItemMapper
import com.ecommerce.adapter.out.persistence.repository.ItemJpaRepository
import com.ecommerce.application.port.out.ItemPort
import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import com.ecommerce.domain.item.Item
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class ItemPersistenceAdapter(
    private val itemMapper: ItemMapper,
    private val jpaRepository: ItemJpaRepository
): ItemPort {

    override fun getItemsByPage(page: Int, size: Int): Page<Item> {
        val items = jpaRepository.findAll(PageRequest.of(page, size))

        return items.map { itemMapper.toItem(it) }
    }

    override fun getItemsIn(itemIds: List<Long>): List<Item> {
        val items = jpaRepository.findAllById(itemIds)

        if (items.size != itemIds.size) throw CustomException(ErrorCode.ITEM_NOT_FOUND)

        return items.map { itemMapper.toItem(it) }
    }

    override fun updateItems(items: List<Item>) {
        jpaRepository.saveAll(
            items.map { itemMapper.toItemEntity(it) }
        )
    }

}