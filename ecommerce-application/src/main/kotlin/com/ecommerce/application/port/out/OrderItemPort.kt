package com.ecommerce.application.port.out

import com.ecommerce.domain.order.OrderItem

interface OrderItemPort {

    fun getOrderItemsByPeriod(period: Long): List<OrderItem>

}