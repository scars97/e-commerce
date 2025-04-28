package com.ecommerce.application.event

import com.ecommerce.application.port.out.DataPlatformPort
import com.ecommerce.domain.event.SendOrderInfoEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class OrderInfoEventListener(
    private val dataPlatformPort: DataPlatformPort
) {

    @Async // TODO 스레드 풀 설정
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun send(event: SendOrderInfoEvent) {
        dataPlatformPort.sendOrderInfo(event)
    }

}