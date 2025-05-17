package com.ecommerce.adapter.usecase

import com.ecommerce.adapter.config.IntegrateTestSupport
import com.ecommerce.adapter.fixture.OrderFixture
import com.ecommerce.adapter.fixture.UserFixture
import com.ecommerce.adapter.out.persistence.repository.StockJpaRepository
import com.ecommerce.adapter.out.persistence.repository.UserCouponJpaRepository
import com.ecommerce.application.dto.OrderCommand
import com.ecommerce.application.port.`in`.OrderUseCase
import com.ecommerce.domain.coupon.UserCoupon
import com.ecommerce.domain.order.Order
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicInteger

class OrderUseCaseTest @Autowired constructor(
    private val sut: OrderUseCase,
    private val orderFixture: OrderFixture,
    private val userFixture: UserFixture,
    private val userCouponRepository: UserCouponJpaRepository,
    private val stockRepository: StockJpaRepository
): IntegrateTestSupport() {

    @DisplayName("주문 생성 테스트")
    @Test
    fun placeOrder() {
        // given
        orderFixture.execute()

        val deductStock = 2L
        val command = OrderCommand(
            userId = 1L,
            couponId = 1L,
            orderItems = listOf(OrderCommand.OrderItemCommand(1L, deductStock))
        )

        // when
        val result = sut.placeOrder(command)

        // then
        assertThat(result)
            .extracting("id", "couponId", "userId", "status")
            .containsExactly(1L, 1L, 1L, Order.OrderStatus.ORDERED)
        verifyOrderPrice(command, result)
        verifyRemainingStock(deductStock)
        verifyUserCoupon()
    }

    private fun verifyUserCoupon() {
        val findUserCoupon = userCouponRepository.findById(orderFixture.getUserCoupon().id!!).get()
        assertThat(findUserCoupon.status).isEqualTo(UserCoupon.UserCouponStatus.USED)
        assertThat(findUserCoupon.usedAt).isNotNull()
    }

    private fun verifyRemainingStock(deductStock: Long) {
        val findStock = stockRepository.findAll()[0]
        val originStockOfItem = orderFixture.getStocks().associate { it.itemId to it.quantity }

        val actualQuantity = findStock.quantity
        val expectQuantity = originStockOfItem[findStock.itemId]!! - deductStock

        assertThat(actualQuantity).isEqualTo(expectQuantity)
    }

    private fun verifyOrderPrice(command: OrderCommand, result: Order) {
        val priceOfItem = orderFixture.getItems().associate { it.id!! to it.price }
        val expectOriginPrice = command.orderItems.sumOf {
            priceOfItem[it.itemId]!! * BigDecimal.valueOf(it.quantity)
        }

        val expectDiscountPrice = expectOriginPrice * BigDecimal.valueOf(orderFixture.getCoupon().discount).divide(BigDecimal.valueOf(100))
        val expectTotalPrice = expectOriginPrice - expectDiscountPrice

        assertThat(result.originPrice.compareTo(expectOriginPrice)).isZero()
        assertThat(result.discountPrice.compareTo(expectDiscountPrice)).isZero()
        assertThat(result.totalPrice.compareTo(expectTotalPrice)).isZero()
    }

    @DisplayName("수량이 50개인 상품을 1개씩 동시 주문하는 경우, 50명은 주문에 성공하지만 나머지는 실패한다.")
    @Test
    fun whenItemQuantityIs50AndOrdersFor1Item_then50UserWillSucceedBut1UserWillFail() {
        // given
        orderFixture.createItems()

        val totalUser = 51
        userFixture.createBulkUsers(totalUser)

        val successCount = AtomicInteger(0)
        val failureCount = AtomicInteger(0)

        // when
        val tasks = (1 .. totalUser).map { userId ->
            CompletableFuture.runAsync {
                try {
                    sut.placeOrder(
                        OrderCommand(
                            userId = userId.toLong(),
                            couponId = null,
                            orderItems = listOf(OrderCommand.OrderItemCommand(1L, 1L))
                        )
                    )
                    successCount.incrementAndGet()
                } catch (e: Exception) {
                    failureCount.incrementAndGet()
                }
            }
        }
        CompletableFuture.allOf(*tasks.toTypedArray()).join()

        // then
        assertThat(successCount.get()).isEqualTo(50)
        assertThat(failureCount.get()).isOne()
    }

}

