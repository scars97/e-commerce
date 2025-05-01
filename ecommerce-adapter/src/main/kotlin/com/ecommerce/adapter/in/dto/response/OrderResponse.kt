package com.ecommerce.adapter.`in`.dto.response

import com.ecommerce.domain.order.Order
import com.ecommerce.domain.order.OrderItem
import java.math.BigDecimal

data class OrderResponse(
    val orderId: Long,
    val couponId: Long?,
    val userId: Long,
    val status: String,
    val originPrice: BigDecimal = BigDecimal.ZERO,
    val discountPrice: BigDecimal = BigDecimal.ZERO,
    val totalPrice: BigDecimal = BigDecimal.ZERO,
    val orderItems: List<OrderItemResponse>
) {

    companion object {
        fun of(order: Order): OrderResponse {
            return OrderResponse(
                orderId = order.id!!,
                couponId = order.couponId,
                userId = order.userId,
                status = order.status.name,
                originPrice = order.originPrice,
                discountPrice = order.discountPrice,
                totalPrice = order.totalPrice,
                orderItems = order.orderItems.map { OrderItemResponse.of(it) }
            )
        }
    }

    data class OrderItemResponse(
        val itemId: Long,
        val quantity: Long
    ) {
        companion object {
            fun of(orderItem: OrderItem): OrderItemResponse {
                return OrderItemResponse(
                    itemId = orderItem.itemId,
                    quantity = orderItem.quantity
                )
            }
        }
    }


}