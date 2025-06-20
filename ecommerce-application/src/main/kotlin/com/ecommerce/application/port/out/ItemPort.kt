package com.ecommerce.application.port.out

import com.ecommerce.domain.item.Item
import org.springframework.data.domain.Page

interface ItemPort {

    fun getItemsByPage(page: Int, size: Int): Page<Item>

    fun getItemsIn(itemIds: List<Long>): List<Item>

    fun updateItem(item: Item)

    fun getPopularItemIds(period: Long): List<Long>

    fun updatePopularItemRank(period: Long, itemIds: List<Long>)

}