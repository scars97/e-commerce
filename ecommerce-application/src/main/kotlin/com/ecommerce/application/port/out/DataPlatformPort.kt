package com.ecommerce.application.port.out

import com.ecommerce.domain.event.OrderInfoEvent

interface DataPlatformPort {

    fun sendOrderInfo(event: OrderInfoEvent)

}