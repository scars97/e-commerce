package com.ecommerce.application.event

import com.ecommerce.application.compensation.CompensateOrderService
import com.ecommerce.application.port.out.ItemPort
import com.ecommerce.application.port.out.OrderPort
import com.ecommerce.application.port.out.StockPort
import com.ecommerce.common.lock.DistributeLockExecutor
import com.ecommerce.domain.event.DeductStockEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class DeductStockEventListener(
    private val itemPort: ItemPort,
    private val stockPort: StockPort,
    private val orderPort: OrderPort,
    private val compensateOrderService: CompensateOrderService,
    private val lockExecutor: DistributeLockExecutor
) {

    @Async("eventTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun deductStock(event: DeductStockEvent) {
        val order = event.order
        val itemOfId = event.items.associateBy { it.id }

        try {
            order.orderItems.forEach {
                lockExecutor.lockWithTransaction(
                    "deductStock-${it.itemId}", 5L, 3L
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

            order.complete()
            orderPort.commandOrder(order)
        } catch (e: Exception) {
            // 보상 트랜잭션 - 쿠폰 사용 취소 , 주문 취소
            compensateOrderService.compensateOrder(order)
        }
    }

}