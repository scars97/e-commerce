package com.ecommerce.adapter.out.persistence.mapper

import com.ecommerce.adapter.out.persistence.entity.ItemEntity
import com.ecommerce.adapter.out.persistence.entity.StockEntity
import com.ecommerce.domain.item.Item
import com.ecommerce.domain.item.Stock

class ItemMapper {

    companion object {
        fun toItemDomain(entity: ItemEntity): Item {
            return Item(
                id = entity.id,
                categoryId = entity.categoryId,
                name = entity.name,
                price = entity.price,
                thumbnail = entity.thumbnail,
                status = entity.status,
                stock = this.toStockDomain(entity.stock),
                createAt = entity.createAt,
                modifiedAt = entity.modifiedAt
            )
        }

        fun toItemEntity(domain: Item): ItemEntity {
            return ItemEntity(
                id = domain.id,
                categoryId = domain.categoryId,
                name = domain.name,
                price = domain.price,
                thumbnail = domain.thumbnail,
                status = domain.status,
                stock = this.toStockEntity(domain.stock)
            )
        }

        fun toStockDomain(entity: StockEntity): Stock {
            return Stock(entity.id, entity.quantity, entity.createAt, entity.modifiedAt)
        }

        fun toStockEntity(domain: Stock): StockEntity {
            return StockEntity(
                id = domain.id,
                quantity = domain.quantity
            )
        }
    }

}
