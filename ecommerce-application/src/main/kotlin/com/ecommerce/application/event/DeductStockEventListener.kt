package com.ecommerce.application.event

import com.ecommerce.application.port.out.ItemPort
import com.ecommerce.application.port.out.StockPort
import com.ecommerce.common.lock.DistributeLockExecutor
import com.ecommerce.domain.event.DeductStockEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class DeductStockEventListener(
    private val itemPort: ItemPort,
    private val stockPort: StockPort,
    private val lockExecutor: DistributeLockExecutor
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun deductStock(event: DeductStockEvent) {
        val itemOfId = event.items.associateBy { it.id }

        event.orderItems.forEach {
            // TODO 상품 Id 별 분산락 적용
            lockExecutor.lockWithTransaction(
                "deductStock-${it.itemId}",
                5L,
                3L
            ) {
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

}