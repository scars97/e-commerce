package com.ecommerce.adapter.out.persistence.core

import com.ecommerce.adapter.out.persistence.mapper.OrderMapper
import com.ecommerce.adapter.out.persistence.repository.OrderItemJpaRepository
import com.ecommerce.application.port.out.OrderItemPort
import com.ecommerce.domain.order.OrderItem
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class OrderItemPersistenceAdapter(
    private val jpaRepository: OrderItemJpaRepository
): OrderItemPort {

    override fun getOrderItemsByPeriod(period: Long): List<OrderItem> {
        val from = LocalDateTime.now().minusDays(period)

        val orderItems = jpaRepository.findByCreateAtGreaterThanEqual(from)

        return orderItems.map { OrderMapper.toOrderItem(it) }
    }

}