package com.ecommerce.adapter.out.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "ORDER_ITEM")
class OrderItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    val id: Long?,

    val itemId: Long,

    val quantity: Long
): BaseEntity()
