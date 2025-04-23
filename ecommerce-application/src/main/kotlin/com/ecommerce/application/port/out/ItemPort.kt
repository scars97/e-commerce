package com.ecommerce.application.port.out

import com.ecommerce.domain.Item
import org.springframework.data.domain.Page

interface ItemPort {

    fun getItemsByPage(page: Int, size: Int): Page<Item>

}