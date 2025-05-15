package com.ecommerce.application.event

import com.ecommerce.application.port.out.StockPort
import com.ecommerce.domain.event.DeductStockEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class DeductStockEventListener(
    private val stockPort: StockPort
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun deductStock(event: DeductStockEvent) {
        val stockOfItem = event.items.associate { it.id to it.stock.id }

        event.orderItems.forEach {
            val stock = stockPort.findStockById(stockOfItem[it.itemId]!!)

            stock.deduct(it.quantity)

            stockPort.deductStock(stock)
        }
    }

}