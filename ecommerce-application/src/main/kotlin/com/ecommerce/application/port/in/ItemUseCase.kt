package com.ecommerce.application.port.`in`

import com.ecommerce.application.dto.ItemResult
import org.springframework.data.domain.Page

interface ItemUseCase {

    fun getItems(page: Int, size: Int): Page<ItemResult>
    
}