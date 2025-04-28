package com.ecommerce.adapter.out.persistence.mapper

import com.ecommerce.adapter.out.persistence.entity.OrderEntity
import com.ecommerce.adapter.out.persistence.entity.OrderItemEntity
import com.ecommerce.domain.order.Order
import com.ecommerce.domain.order.OrderItem

class OrderMapper {

    companion object {
        fun toOrderEntity(domain: Order): OrderEntity {
            return OrderEntity(
                id = domain.id ?:0,
                couponId = domain.couponId,
                userId = domain.userId,
                orderItems = domain.orderItems.map { toOrderItemEntity(it) },
                originPrice = domain.originPrice,
                discountPrice = domain.discountPrice,
                totalPrice = domain.totalPrice,
                status = domain.status
            )
        }

        fun toOrder(entity: OrderEntity): Order {
            return Order(
                id= entity.id,
                couponId= entity.couponId,
                userId= entity.userId,
                orderItems= entity.orderItems.map { toOrderItem(it) }.toList(),
                originPrice= entity.originPrice,
                discountPrice= entity.discountPrice,
                totalPrice= entity.totalPrice,
                status= entity.status
            )
        }

        fun toOrderItemEntity(domain: OrderItem): OrderItemEntity {
            return OrderItemEntity(domain.itemId, domain.quantity)
        }

        fun toOrderItem(entity: OrderItemEntity): OrderItem {
            return OrderItem(entity.id, entity.itemId, entity.quantity)
        }
    }

}