package com.ecommerce.application.port.out

import com.ecommerce.domain.event.SendOrderInfoEvent

interface DataPlatformPort {

    fun sendOrderInfo(event: SendOrderInfoEvent)

}