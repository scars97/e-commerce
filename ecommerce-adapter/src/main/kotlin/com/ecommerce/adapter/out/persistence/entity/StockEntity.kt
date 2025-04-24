package com.ecommerce.adapter.out.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "STOCK")
class StockEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    val id: Long,

    var quantity: Long
): BaseEntity() {

    constructor(quantity: Long): this(0, quantity)

}