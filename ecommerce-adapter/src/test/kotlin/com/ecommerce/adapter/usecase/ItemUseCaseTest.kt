package com.ecommerce.adapter.usecase

import com.ecommerce.adapter.config.IntegrateTestSupport
import com.ecommerce.adapter.fixture.OrderFixture
import com.ecommerce.application.dto.OrderCommand
import com.ecommerce.application.port.`in`.ItemUseCase
import com.ecommerce.application.port.`in`.OrderUseCase
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ItemUseCaseTest @Autowired constructor(
    private val sut: ItemUseCase,
    private val orderUseCase: OrderUseCase,
    private val orderFixture: OrderFixture
): IntegrateTestSupport() {

    @BeforeEach
    fun setUp() {
        orderFixture.execute()

        orderUseCase.placeOrder(
            OrderCommand(
                userId = 1L,
                couponId = null,
                orderItems = listOf(
                    OrderCommand.OrderItemCommand(1L, 15L),
                    OrderCommand.OrderItemCommand(2L, 18L),
                    OrderCommand.OrderItemCommand(3L, 10L),
                    OrderCommand.OrderItemCommand(4L, 12L),
                    OrderCommand.OrderItemCommand(5L, 20L),
                )
            )
        )
    }

    @Test
    fun getPopularItemsOnTop10() {
        // given
        val period = 3L

        // when
        val popularItems = sut.getPopularItemsOnTop10(period)

        // then
        assertThat(popularItems).hasSize(5)
            .extracting("rank", "cumulativeSales", "item.id", "item.name")
            .containsExactly(
                tuple(1, 20L, 5L, "상품 E"),
                tuple(2, 18L, 2L, "상품 B"),
                tuple(3, 15L, 1L, "상품 A"),
                tuple(4, 12L, 4L, "상품 D"),
                tuple(5, 10L, 3L, "상품 C")
            )
    }

}