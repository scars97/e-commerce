package com.ecommerce.adapter.out.persistence.entity

import com.ecommerce.domain.order.Order
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "ORDERS")
class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    val id: Long,

    val couponId: Long?,

    val userId: Long,

    @OneToMany(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "order_Id")
    val orderItems: List<OrderItemEntity> = listOf(),

    var originPrice: BigDecimal = BigDecimal.ZERO,

    var discountPrice: BigDecimal = BigDecimal.ZERO,

    var totalPrice: BigDecimal = BigDecimal.ZERO,

    var status: Order.OrderStatus
): BaseEntity()