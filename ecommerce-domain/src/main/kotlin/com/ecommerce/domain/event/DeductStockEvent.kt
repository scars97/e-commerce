package com.ecommerce.domain.event

import com.ecommerce.domain.item.Item
import com.ecommerce.domain.order.Order

data class DeductStockEvent(
    val items: List<Item>,
    val order: Order
)