package com.ecommerce.adapter.out.persistence.core

import com.ecommerce.adapter.out.persistence.mapper.ItemMapper
import com.ecommerce.adapter.out.persistence.repository.ItemJpaRepository
import com.ecommerce.application.port.out.ItemPort
import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import com.ecommerce.domain.item.Item
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class ItemPersistenceAdapter(
    private val itemMapper: ItemMapper,
    private val jpaRepository: ItemJpaRepository,
    private val redisTemplate: RedisTemplate<String, String>
): ItemPort {

    companion object {
        const val POPULAR_ITEMS = "popular-items-"
    }

    override fun getItemsByPage(page: Int, size: Int): Page<Item> {
        val items = jpaRepository.findAll(PageRequest.of(page, size))

        return items.map { itemMapper.toItem(it) }
    }

    override fun getItemsIn(itemIds: List<Long>): List<Item> {
        val items = jpaRepository.findAllById(itemIds)

        if (items.size != itemIds.size) throw CustomException(ErrorCode.ITEM_NOT_FOUND)

        return items.map { itemMapper.toItem(it) }
    }

    override fun getItems(itemId: Long): Item {
        val item = jpaRepository.findById(itemId)
            .orElseThrow { CustomException(ErrorCode.ITEM_NOT_FOUND) }

        return itemMapper.toItem(item)
    }

    override fun updateItem(item: Item) {
        jpaRepository.save(itemMapper.toItemEntity(item))
    }

    override fun getPopularItemIds(period: Long): List<Long> {
        val key = POPULAR_ITEMS.plus(period)

        val json = redisTemplate.opsForValue().get(key)

        return if (json != null) {
            val trimmed = json.removePrefix("[").removeSuffix("]")
            trimmed.split(",").map { it.trim().toLong() }
        } else {
            emptyList()
        }
    }

    override fun updatePopularItemRank(period: Long, itemIds: List<Long>) {
        val key = POPULAR_ITEMS.plus(period)

        val stringItemIds = itemIds.toString()

        redisTemplate.opsForValue().set(key, stringItemIds)
    }

}