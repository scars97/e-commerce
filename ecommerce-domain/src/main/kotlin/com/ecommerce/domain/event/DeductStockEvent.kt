package com.ecommerce.domain.event

import com.ecommerce.domain.item.Item
import com.ecommerce.domain.order.OrderItem

data class DeductStockEvent(
    val items: List<Item>,
    val orderItems: List<OrderItem>
)