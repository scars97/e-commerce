package com.ecommerce.application.port.`in`

import com.ecommerce.domain.item.Item
import org.springframework.data.domain.Page

interface ItemUseCase {

    fun getItems(page: Int, size: Int): Page<Item>
    
}