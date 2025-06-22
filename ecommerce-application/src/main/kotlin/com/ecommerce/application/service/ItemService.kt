package com.ecommerce.application.service

import com.ecommerce.application.port.`in`.ItemUseCase
import com.ecommerce.application.port.out.ItemPort
import com.ecommerce.application.port.out.OrderItemPort
import com.ecommerce.domain.item.Item
import com.ecommerce.domain.item.PopularItem
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ItemService(
    private val itemPort: ItemPort,
    private val orderItemPort: OrderItemPort
): ItemUseCase {

    companion object {
        const val ITEM_LIMIT = 10
    }

    override fun getItems(page: Int, size: Int): Page<Item> {
        return itemPort.getItemsByPage(page, size)
    }

    override fun getPopularItems(period: Long): List<PopularItem> {
        val itemIds = itemPort.getPopularItemIds(period)

        val items = itemIds.map { itemPort.getItems(it) }.toList()

        return convertPopularItems(items)
    }

    private fun convertPopularItems(items: List<Item>): List<PopularItem> {
        return items.mapIndexed { index, item ->
            PopularItem(
                rank = index + 1,
                item = item
            )
        }
    }

}