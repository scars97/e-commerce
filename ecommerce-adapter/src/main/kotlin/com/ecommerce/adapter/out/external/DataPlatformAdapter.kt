package com.ecommerce.adapter.out.external

import com.ecommerce.application.port.out.DataPlatformPort
import com.ecommerce.domain.event.OrderInfoEvent
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class DataPlatformAdapter: DataPlatformPort {

    private val log = LoggerFactory.getLogger(DataPlatformAdapter::class.java)

    override fun sendOrderInfo(event: OrderInfoEvent) {
        val message = "${OrderInfoEvent::class.simpleName} - ${event.order.id}"

        try {
            log.info("데이터 플랫폼 전송 성공 : $message")
        } catch (e: Exception) {
            log.error("데이터 플랫폼 전송 실패 : $message")
            throw Exception()
        }
    }

}