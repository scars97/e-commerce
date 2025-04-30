package com.ecommerce.application.service

import com.ecommerce.application.port.`in`.ItemUseCase
import com.ecommerce.application.port.out.ItemPort
import com.ecommerce.application.port.out.OrderItemPort
import com.ecommerce.domain.item.Item
import com.ecommerce.domain.item.PopularItem
import com.ecommerce.domain.order.OrderItem
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemService(
    private val itemPort: ItemPort,
    private val orderItemPort: OrderItemPort
): ItemUseCase {

    @Transactional(readOnly = true)
    override fun getItems(page: Int, size: Int): Page<Item> {
        return itemPort.getItemsByPage(page, size)
    }

    @Transactional(readOnly = true)
    override fun getPopularItems(period: Long, page: Int, size: Int): Page<PopularItem> {
        // period 만큼 주문 상품 전체 조회
        val orderItemsByPeriodAndPage = orderItemPort.getOrderItemsByPeriod(period, page, size)
        val orderItemsByPeriod = orderItemsByPeriodAndPage.content

        // 주문 수량 기준으로 상품 정렬
        val orderItems = orderItemsByPeriod
            .groupBy { it.itemId }
            // 중복 제거 후, 주문 수량 합 계산
            .map { (itemId, items) ->
                val totalQuantity = items.sumOf { it.quantity }
                OrderItem(itemId, totalQuantity)
            }
            .sortedByDescending { it.quantity }

        // 정렬된 주문 상품의 id로 실제 상품 조회
        val itemIds = orderItems.map { it.itemId }
        val items = itemPort.getItemsIn(itemIds)

        // 인기 상품 변환
        val itemOfId = items.associateBy { it.id }
        val popularItems = orderItems.mapIndexed { index, item ->
            PopularItem(
                rank = page * (index + 1),
                cumulativeSales = item.quantity,
                item = itemOfId[item.id]!!
            )
        }

        return PageImpl(
            popularItems,
            PageRequest.of(page, size),
            popularItems.size.toLong()
        )
    }

}