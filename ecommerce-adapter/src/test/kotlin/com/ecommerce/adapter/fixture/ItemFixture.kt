package com.ecommerce.adapter.fixture

import com.ecommerce.adapter.out.persistence.entity.ItemEntity
import com.ecommerce.adapter.out.persistence.entity.StockEntity
import com.ecommerce.adapter.out.persistence.repository.ItemJpaRepository
import com.ecommerce.adapter.out.persistence.repository.StockJpaRepository
import com.ecommerce.domain.item.Item
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class ItemFixture(
    private val itemRepository: ItemJpaRepository,
    private val stockRepository: StockJpaRepository
) {

    private lateinit var items: List<ItemEntity>
    private lateinit var stocks: List<StockEntity>

    fun createItemsAndStocks(quantity: Long): List<ItemEntity> {
        items = itemRepository.saveAll(
            listOf(
                ItemEntity(null, 1L, "상품 A", BigDecimal.valueOf(15000L),"http://test.png", Item.ItemStatus.SELLING),
                ItemEntity(null, 1L, "상품 B", BigDecimal.valueOf(20000L),"http://test.png", Item.ItemStatus.SELLING),
                ItemEntity(null, 1L, "상품 C", BigDecimal.valueOf(17000L),"http://test.png", Item.ItemStatus.SELLING),
                ItemEntity(null, 1L, "상품 D", BigDecimal.valueOf(9000L),"http://test.png", Item.ItemStatus.SELLING),
                ItemEntity(null, 1L, "상품 E", BigDecimal.valueOf(10000L),"http://test.png", Item.ItemStatus.SELLING)
            )
        )

        stocks = items.map {
            stockRepository.save(StockEntity(null, it.id!!,quantity))
        }

        return items
    }

    fun getItems(): List<ItemEntity> {
        return this.items
    }

    fun getStocks(): List<StockEntity> {
        return this.stocks
    }

}