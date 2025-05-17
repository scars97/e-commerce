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
        val itemOfId = event.items.associateBy { it.id }

        event.orderItems.forEach {
            val stock = stockPort.findStockByItemId(it.itemId)

            if (stock.deduct(it.quantity) == 0L) {
                val item = itemOfId[it.itemId]!!
                item.soldOut()
                itemPort.updateItem(item)
            }

            stockPort.deductStock(stock)
        }
    }

}