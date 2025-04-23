package com.ecommerce.application.service

import com.ecommerce.application.dto.ItemResult
import com.ecommerce.application.port.`in`.ItemUseCase
import com.ecommerce.application.port.out.ItemPort
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class ItemService(
    private val itemPort: ItemPort
): ItemUseCase {

    override fun getItems(page: Int, size: Int): Page<ItemResult> {
        val items = itemPort.getItemsByPage(page, size)

        return items.map { ItemResult.of(it) }
    }

}