package com.ecommerce.adapter.usecase

import com.ecommerce.adapter.config.IntegrateTestSupport
import com.ecommerce.adapter.fixture.OrderFixture
import com.ecommerce.adapter.out.persistence.repository.ItemJpaRepository
import com.ecommerce.adapter.out.persistence.repository.UserCouponJpaRepository
import com.ecommerce.application.dto.OrderCommand
import com.ecommerce.application.port.`in`.OrderUseCase
import com.ecommerce.domain.coupon.UserCoupon
import com.ecommerce.domain.order.Order
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal

class OrderUseCaseTest @Autowired constructor(
    private val sut: OrderUseCase,
    private val orderFixture: OrderFixture,
    private val userCouponRepository: UserCouponJpaRepository,
    private val itemRepository: ItemJpaRepository
): IntegrateTestSupport() {

    @BeforeEach
    fun setUp() {
        orderFixture.execute()
    }

    @Test
    fun placeOrder() {
        // given
        val deductStock = 2L
        val command = OrderCommand(
            userId = 1L,
            couponId = 1L,
            orderItems = listOf(
                OrderCommand.OrderItemCommand(1L, deductStock),
                OrderCommand.OrderItemCommand(2L, deductStock),
                OrderCommand.OrderItemCommand(3L, deductStock)
            )
        )

        // when
        val result = sut.placeOrder(command)

        // then
        assertThat(result)
            .extracting("id", "couponId", "userId", "status")
            .containsExactly(1L, 1L, 1L, Order.OrderStatus.ORDERED)
        verifyOrderPrice(command, result)
        verifyRemainingStock(command, deductStock)
        verifyUserCoupon()
    }

    private fun verifyUserCoupon() {
        val findUserCoupon = userCouponRepository.findById(orderFixture.getUserCoupon().id!!).get()
        assertThat(findUserCoupon.status).isEqualTo(UserCoupon.UserCouponStatus.USED)
        assertThat(findUserCoupon.usedAt).isNotNull()
    }

    private fun verifyRemainingStock(command: OrderCommand, deductStock: Long) {
        val findItems = itemRepository.findAllById(command.orderItems.map { it.itemId })
        val originStockOfItem = orderFixture.getItems().associate { it.id to it.stock }

        findItems.forEach {
            val actualQuantity = it.stock.quantity
            val expectQuantity = originStockOfItem[it.id]!!.quantity - deductStock

            assertThat(actualQuantity).isEqualTo(expectQuantity)
        }
    }

    private fun verifyOrderPrice(command: OrderCommand, result: Order) {
        val priceOfItem = orderFixture.getItems().associate { it.id!! to it.price }
        val expectOriginPrice = command.orderItems.sumOf {
            priceOfItem[it.itemId]!! * BigDecimal.valueOf(it.quantity)
        }
        val expectDiscountPrice =
            expectOriginPrice * BigDecimal.valueOf(orderFixture.getCoupon().discount).divide(BigDecimal.valueOf(100))
        val expectTotalPrice = expectOriginPrice - expectDiscountPrice

        assertThat(result.originPrice.compareTo(expectOriginPrice)).isZero()
        assertThat(result.discountPrice.compareTo(expectDiscountPrice)).isZero()
        assertThat(result.totalPrice.compareTo(expectTotalPrice)).isZero()
    }

}

