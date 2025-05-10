package com.ecommerce.adapter.out.persistence.mapper

import com.ecommerce.adapter.out.persistence.entity.OrderEntity
import com.ecommerce.adapter.out.persistence.entity.OrderItemEntity
import com.ecommerce.domain.order.Order
import com.ecommerce.domain.order.OrderItem
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface OrderMapper {

    fun toOrder(entity: OrderEntity): Order

    fun toOrderItem(entity: OrderItemEntity): OrderItem

    @Mappings(
        Mapping(target = "createAt", ignore = true),
        Mapping(target = "modifiedAt", ignore = true)
    )
    fun toOrderEntity(domain: Order): OrderEntity

    @Mappings(
        Mapping(target = "createAt", ignore = true),
        Mapping(target = "modifiedAt", ignore = true)
    )
    fun toOrderItemEntity(domain: OrderItem): OrderItemEntity

}