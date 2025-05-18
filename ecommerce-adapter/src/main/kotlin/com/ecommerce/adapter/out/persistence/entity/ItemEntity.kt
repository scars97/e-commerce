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
    val id: Long?,

    val categoryId: Long,

    var name: String,

    var price: BigDecimal,

    var thumbnail: String,

    @Enumerated(EnumType.STRING)
    var status: Item.ItemStatus
): BaseEntity()