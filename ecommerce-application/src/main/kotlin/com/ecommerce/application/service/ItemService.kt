package com.ecommerce.application.service

import com.ecommerce.application.port.`in`.ItemUseCase
import com.ecommerce.application.port.out.ItemPort
import com.ecommerce.domain.item.Item
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemService(
    private val itemPort: ItemPort
): ItemUseCase {

    @Transactional(readOnly = true)
    override fun getItems(page: Int, size: Int): Page<Item> {
        return itemPort.getItemsByPage(page, size)
    }

}