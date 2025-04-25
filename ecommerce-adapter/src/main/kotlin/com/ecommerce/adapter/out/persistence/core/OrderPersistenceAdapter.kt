package com.ecommerce.adapter.out.persistence.core

import com.ecommerce.adapter.out.persistence.mapper.OrderMapper
import com.ecommerce.adapter.out.persistence.repository.OrderJpaRepository
import com.ecommerce.application.port.out.OrderPort
import com.ecommerce.domain.order.Order
import org.springframework.stereotype.Repository

@Repository
class OrderPersistenceAdapter(
    private val jpaRepository: OrderJpaRepository
): OrderPort {

    override fun createOrder(order: Order): Order {
        val orderEntity = OrderMapper.toOrderEntity(order)

        val saveOrder = jpaRepository.save(orderEntity)

        return OrderMapper.toOrder(saveOrder)
    }

}