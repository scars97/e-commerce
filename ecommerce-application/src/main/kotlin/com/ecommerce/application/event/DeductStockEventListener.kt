package com.ecommerce.application.event

import com.ecommerce.application.port.out.ItemPort
import com.ecommerce.application.port.out.StockPort
import com.ecommerce.domain.event.DeductStockEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class DeductStockEventListener(
    private val itemPort: ItemPort,
    private val stockPort: StockPort
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun deductStock(event: DeductStockEvent) {
        val orderItem = event.orderItem

        val stock = stockPort.findStockByItemId(orderItem.itemId)

        if (stock.deduct(orderItem.quantity) == 0L) {
            val item = event.item
            item.soldOut()
            itemPort.updateItem(item)
        }

        stockPort.deductStock(stock)
    }

}