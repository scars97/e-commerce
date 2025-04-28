package com.ecommerce.domain.event

import com.ecommerce.domain.order.Order

data class SendOrderInfoEvent(
    val order: Order
)
