package com.ecommerce.adapter.service

import com.ecommerce.adapter.config.IntegrateTestSupport
import com.ecommerce.adapter.fixture.OrderFixture
import com.ecommerce.application.port.out.ItemPort
import com.ecommerce.application.schedule.ItemRankSchedule
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ItemRankScheduleTest @Autowired constructor(
    private val sut: ItemRankSchedule,
    private val itemPort: ItemPort,
    private val orderFixture: OrderFixture
): IntegrateTestSupport() {

    @BeforeEach
    fun setUp() {
        orderFixture.placeOrder(OrderFixture.OrderFixtureStatus.BULK)
    }

    @DisplayName("스케줄러를 통해 특정 기간동안 주문된 상품을 집계하여 인기 상품 순위를 결정한다.")
    @Test
    fun whenExecuteScheduler_thenAggregateOrderStatistics() {
        // given
        val period = 3L

        // when
        sut.aggregateOrderStatistics()

        // then
        val result = itemPort.getPopularItemIds(period)
        assertThat(result).hasSize(5)
            .containsExactly(5L, 2L, 1L, 4L, 3L)
    }

}