package com.ecommerce.application.dto

import com.ecommerce.domain.order.Order
import com.ecommerce.domain.order.OrderItem
import java.math.BigDecimal

data class OrderCommand(
    val userId: Long,
    val couponId: Long?,
    val orderItems: List<OrderItemCommand>
) {

    fun toOrder(): Order {
        return Order(
            id = null,
            couponId = this.couponId,
            userId = this.userId,
            orderItems = this.orderItems.map { it.toOrderItem() }
        )
    }

    data class OrderItemCommand(
        val itemId: Long,
        val quantity: Long
    ) {
        fun toOrderItem(): OrderItem {
            return OrderItem(
                null,
                this.itemId,
                this.quantity
            )
        }
    }

}
