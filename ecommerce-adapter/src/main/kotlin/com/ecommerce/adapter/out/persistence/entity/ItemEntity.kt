package com.ecommerce.adapter.out.persistence.entity

import com.ecommerce.domain.Item
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "ITEM")
class ItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    val id: Long,

    val categoryId: Long,

    val name: String,

    val price: BigDecimal,

    val thumbnail: String,

    val status: Item.ItemStatus,

    @CreatedDate
    val createAt: LocalDateTime,

    @LastModifiedDate
    val modifiedAt: LocalDateTime
) {

    constructor(
        categoryId: Long,
        name: String,
        price: BigDecimal,
        thumbnail: String,
        status: Item.ItemStatus,
        createAt: LocalDateTime,
        modifiedAt: LocalDateTime
    ): this(0, categoryId, name, price, thumbnail, status, createAt, modifiedAt)

    fun toDomain(): Item {
        return Item(
            id = this.id,
            categoryId = this.categoryId,
            name = this.name,
            price = this.price,
            thumbnail = this.thumbnail,
            status = this.status,
            createAt = this.createAt,
            modifiedAt = this.modifiedAt
        )
    }

}