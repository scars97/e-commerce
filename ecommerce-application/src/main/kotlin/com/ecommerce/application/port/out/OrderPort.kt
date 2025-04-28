package com.ecommerce.application.port.out

import com.ecommerce.domain.order.Order

interface OrderPort {

    fun commandOrder(order: Order): Order

}