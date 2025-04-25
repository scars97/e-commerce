package com.ecommerce.application.port.out

import com.ecommerce.domain.item.Item
import org.springframework.data.domain.Page

interface ItemPort {

    fun getItemsByPage(page: Int, size: Int): Page<Item>

    fun getItemsIn(itemIds: List<Long>): List<Item>

    fun updateItems(items: List<Item>)

}