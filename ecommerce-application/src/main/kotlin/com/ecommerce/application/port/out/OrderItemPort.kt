package com.ecommerce.application.port.out

import com.ecommerce.domain.order.OrderItem
import org.springframework.data.domain.Page

interface OrderItemPort {

    fun getOrderItemsByPeriod(period: Long, page: Int, size: Int): Page<OrderItem>

}