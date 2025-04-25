package com.ecommerce.application.port.`in`

import com.ecommerce.application.dto.OrderCommand
import com.ecommerce.domain.order.Order

interface OrderUseCase {

    fun placeOrder(orderCommand: OrderCommand): Order

}