package com.ecommerce.adapter.persistence

import com.ecommerce.adapter.config.IntegrateTestSupport
import com.ecommerce.adapter.out.persistence.entity.ItemEntity
import com.ecommerce.adapter.out.persistence.entity.StockEntity
import com.ecommerce.adapter.out.persistence.repository.ItemJpaRepository
import com.ecommerce.adapter.out.persistence.repository.StockJpaRepository
import com.ecommerce.application.port.out.ItemPort
import com.ecommerce.domain.item.Item
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import java.math.BigDecimal

class ItemPersistenceAdapterTest @Autowired constructor(
    private val sut: ItemPort,
    private val itemJpaRepository: ItemJpaRepository,
    private val stockJpaRepository: StockJpaRepository,
    private val redisTemplate: RedisTemplate<String, String>
): IntegrateTestSupport() {

    @BeforeEach
    fun setUp() {
        // item
        val items = itemJpaRepository.saveAll(listOf(
            ItemEntity(null, 1L, "test1", BigDecimal.valueOf(10000L), "http://test.png", Item.ItemStatus.SELLING),
            ItemEntity(null, 1L, "test2", BigDecimal.valueOf(10000L), "http://test.png", Item.ItemStatus.SELLING),
            ItemEntity(null, 1L, "test3", BigDecimal.valueOf(10000L), "http://test.png", Item.ItemStatus.SELLING),
            ItemEntity(null, 1L, "test4", BigDecimal.valueOf(10000L), "http://test.png", Item.ItemStatus.SELLING),
            ItemEntity(null, 1L, "test5", BigDecimal.valueOf(10000L), "http://test.png", Item.ItemStatus.SELLING),
        ))

        // stock
        items.forEach {
            stockJpaRepository.save(StockEntity(null, it.id!!, 10))
        }
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

    @TestFactory
    fun popularItemRankCommandAndQueryTest(): List<DynamicTest> {
        val period = 3L
        val redisKey = "popular-items-".plus(period)

        return listOf(
            DynamicTest.dynamicTest("인기 상품 순위 업데이트") {
                val itemIds = listOf(1L, 2L, 3L)

                sut.updatePopularItemRank(period, itemIds)

                val result = redisTemplate.opsForValue().get(redisKey)
                assertThat(result).isEqualTo("[1, 2, 3]")
            },
            DynamicTest.dynamicTest("인기 상품 조회") {
                val result = sut.getPopularItemIds(period)

                assertThat(result).hasSize(3)
                    .containsExactly(1L, 2L, 3L)
            }
        )
    }

}