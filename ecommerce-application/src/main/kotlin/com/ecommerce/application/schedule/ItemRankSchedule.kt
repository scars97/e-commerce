package com.ecommerce.application.schedule

import com.ecommerce.application.port.out.ItemPort
import com.ecommerce.application.port.out.OrderItemPort
import com.ecommerce.domain.order.OrderItem
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ItemRankSchedule(
    private val orderItemPort: OrderItemPort,
    private val itemPort: ItemPort
) {

    companion object {
        val PERIOD_LIST = listOf(3L, 7L)
    }

    @Scheduled(cron = "0 0 0 * * *")
    fun aggregateOrderStatistics() {
        PERIOD_LIST.forEach {
            // 특정 기간동안 주문된 상품 조회
            val orderItemsByPeriod = orderItemPort.getOrderItemsByPeriod(it)

            // 주문 수량이 많은 순으로 정렬
            val sortedPopularItemIds = deduplicateAfterCalculateTotalQuantity(orderItemsByPeriod)

            // 인기 상품 ID 저장
            itemPort.updatePopularItemRank(it, sortedPopularItemIds)
        }
    }

    private fun deduplicateAfterCalculateTotalQuantity(orderItemsByPeriod: List<OrderItem>) =
        orderItemsByPeriod
            .groupBy { it.itemId }
            .map { (itemId, items) ->
                val totalQuantity = items.sumOf { it.quantity }
                OrderItem(null, itemId, totalQuantity)
            }
            .sortedByDescending { it.quantity }
            .map { it.itemId }

}