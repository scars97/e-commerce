package com.ecommerce.application.event

import com.ecommerce.application.port.out.PointHistoryPort
import com.ecommerce.domain.event.CreatePointHistory
import com.ecommerce.domain.user.PointHistory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class CreatePointHistoryEventListener(
    private val pointHistoryPort: PointHistoryPort
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun createPointHistory(event: CreatePointHistory) {
        pointHistoryPort.saveHistory(
            PointHistory.create(event.userId, event.price, event.status)
        )
    }

}