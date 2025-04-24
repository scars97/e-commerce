package com.ecommerce.adapter.persistence

import com.ecommerce.adapter.config.IntegrateTestSupport
import com.ecommerce.adapter.out.persistence.entity.ItemEntity
import com.ecommerce.adapter.out.persistence.entity.StockEntity
import com.ecommerce.adapter.out.persistence.repository.ItemJpaRepository
import com.ecommerce.application.port.out.ItemPort
import com.ecommerce.domain.item.Item
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal

class ItemPersistenceAdapterTest @Autowired constructor(
    private val sut: ItemPort,
    private val itemJpaRepository: ItemJpaRepository
): IntegrateTestSupport() {

    @BeforeEach
    fun setUp() {
        val items = listOf(
            ItemEntity(1L, "test1", BigDecimal.valueOf(10000L), "http://test.png", Item.ItemStatus.SELLING, StockEntity(10)),
            ItemEntity(1L, "test2", BigDecimal.valueOf(10000L), "http://test.png", Item.ItemStatus.SELLING, StockEntity(10)),
            ItemEntity(1L, "test3", BigDecimal.valueOf(10000L), "http://test.png", Item.ItemStatus.SELLING, StockEntity(10)),
            ItemEntity(1L, "test4", BigDecimal.valueOf(10000L), "http://test.png", Item.ItemStatus.SELLING, StockEntity(10)),
            ItemEntity(1L, "test5", BigDecimal.valueOf(10000L), "http://test.png", Item.ItemStatus.SELLING, StockEntity(10)),
        )
        itemJpaRepository.saveAll(items)
    }

    @DisplayName("총 5개의 상품이 있고 페이지 별 데이터가 2건씩 노출될 때, 마지막 페이지에 노출되는 데이터는 1건이다.")
    @Test
    fun when5ProductAnd2DataImpressionsPerPage_thenLastPageHave1DataImpression() {
        // given
        val page = 2
        val size = 2

        // when
        val result = sut.getItemsByPage(page, size)

        // then
        assertThat(result.size).isEqualTo(size)
        assertThat(result.number).isEqualTo(page)
        assertThat(result.totalPages).isEqualTo(3)
        assertThat(result.totalElements).isEqualTo(5)
        assertThat(result.content).hasSize(1)
    }

}