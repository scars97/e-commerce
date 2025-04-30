package com.ecommerce.application.service

import com.ecommerce.application.port.`in`.ItemUseCase
import com.ecommerce.application.port.out.ItemPort
import com.ecommerce.application.port.out.OrderItemPort
import com.ecommerce.domain.item.Item
import com.ecommerce.domain.item.PopularItem
import com.ecommerce.domain.order.OrderItem
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemService(
    private val itemPort: ItemPort,
    private val orderItemPort: OrderItemPort
): ItemUseCase {

    companion object {
        const val ITEM_LIMIT = 10
    }

    @Transactional(readOnly = true)
    override fun getItems(page: Int, size: Int): Page<Item> {
        return itemPort.getItemsByPage(page, size)
    }

    @Transactional(readOnly = true)
    override fun getPopularItemsOnTop10(period: Long): List<PopularItem> {
        // period 만큼 주문 상품 전체 조회
        val orderItemsByPeriod = orderItemPort.getOrderItemsByPeriod(period)

        // 중복 제거 후, 주문 수량 합 계산
        val orderItems = orderItemsByPeriod
            .groupBy { it.itemId }
            .map { (itemId, items) ->
                val totalQuantity = items.sumOf { it.quantity }
                OrderItem(itemId, totalQuantity)
            }
            .sortedByDescending { it.quantity }
            .take(ITEM_LIMIT)

        // 정렬된 주문 상품의 id로 실제 상품 조회
        val items = itemPort.getItemsIn(
            orderItems.map { it.itemId }
        )

        // 인기 상품 변환
        val itemOfId = items.associateBy { it.id }
        return orderItems.mapIndexed { index, item ->
            PopularItem(
                rank = index + 1,
                cumulativeSales = item.quantity,
                item = itemOfId[item.id]!!
            )
        }.toList()
    }

}