package com.ecommerce.adapter.usecase

import com.ecommerce.adapter.config.IntegrateTestSupport
import com.ecommerce.adapter.fixture.OrderFixture
import com.ecommerce.application.port.`in`.ItemUseCase
import com.ecommerce.application.schedule.ItemRankSchedule
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ItemUseCaseTest @Autowired constructor(
    private val sut: ItemUseCase,
    private val rankScheduler: ItemRankSchedule,
    private val orderFixture: OrderFixture
): IntegrateTestSupport() {

    @BeforeEach
    fun setUp() {
        orderFixture.placeOrder(OrderFixture.OrderFixtureStatus.BULK)
    }

    @Test
    fun getPopularItems() {
        // given
        val period = 3L
        rankScheduler.aggregateOrderStatistics()

        // when
        val result = sut.getPopularItems(period)

        // then
        assertThat(result).hasSize(5)
            .extracting("rank", "item.id", "item.name")
            .containsExactly(
                tuple(1, 5L, "상품 E"),
                tuple(2, 2L, "상품 B"),
                tuple(3, 1L, "상품 A"),
                tuple(4, 4L, "상품 D"),
                tuple(5, 3L, "상품 C")
            )
    }

}