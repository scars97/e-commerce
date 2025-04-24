package com.ecommerce.adapter.out.persistence.entity

import com.ecommerce.domain.item.Item
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "ITEM")
class ItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    val id: Long,

    val categoryId: Long,

    var name: String,

    var price: BigDecimal,

    var thumbnail: String,

    var status: Item.ItemStatus,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "stock_id")
    val stock: StockEntity,
): BaseEntity() {

    constructor(
        categoryId: Long,
        name: String,
        price: BigDecimal,
        thumbnail: String,
        status: Item.ItemStatus,
        stock: StockEntity
    ): this(0, categoryId, name, price, thumbnail, status, stock)

}