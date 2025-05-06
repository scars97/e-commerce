package com.ecommerce.adapter.out.persistence.core

import com.ecommerce.adapter.out.persistence.mapper.OrderMapper
import com.ecommerce.adapter.out.persistence.repository.OrderJpaRepository
import com.ecommerce.application.port.out.OrderPort
import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import com.ecommerce.domain.order.Order
import org.springframework.stereotype.Repository

@Repository
class OrderPersistenceAdapter(
    private val orderMapper: OrderMapper,
    private val jpaRepository: OrderJpaRepository
): OrderPort {

    override fun commandOrder(order: Order): Order {
        val orderEntity = orderMapper.toOrderEntity(order)

        val saveOrder = jpaRepository.save(orderEntity)

        return orderMapper.toOrder(saveOrder)
    }

    override fun findOrderById(orderId: Long): Order {
        val order = jpaRepository.findById(orderId).orElseThrow { CustomException(ErrorCode.ORDER_NOT_FOUND) }

        return orderMapper.toOrder(order)
    }

}