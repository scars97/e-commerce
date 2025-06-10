package com.ecommerce.domain

import com.ecommerce.domain.coupon.Coupon
import com.ecommerce.domain.item.Item
import com.ecommerce.domain.item.Stock
import com.ecommerce.domain.order.Order
import com.ecommerce.domain.order.OrderItem
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.math.BigDecimal

class OrderTest {

    private lateinit var items: List<Item>
    private lateinit var stocks: List<Stock>

    @BeforeEach
    fun setUp() {
        items = listOf(
            Item(1L, 1L, "상품 A", BigDecimal.valueOf(10000L), "http://test.png", Item.ItemStatus.SELLING),
            Item(2L, 1L, "상품 B", BigDecimal.valueOf(5000L), "http://test.png", Item.ItemStatus.SELLING),
            Item(3L, 1L, "상품 C", BigDecimal.valueOf(7000L), "http://test.png", Item.ItemStatus.SELLING)
        )

        stocks = items.map {
            Stock(1L, it.id, 10L)
        }
    }

    @DisplayName("주문 금액 계산")
    @TestFactory
    fun calculateOrderPrice(): List<DynamicTest> {
        // given
        val orderQuantity = 2L
        val orderItem = items.map { OrderItem(null, it.id, orderQuantity) }
        val order = Order(null, 1L, 1L, orderItem)

        var expectOriginPrice = BigDecimal.ZERO

        return listOf(
            DynamicTest.dynamicTest("총 주문 금액이 계산 된다.") {
                // when
                order.calculateOriginPrice(items)

                // then
                expectOriginPrice = items.sumOf {
                     it.price * (BigDecimal.valueOf(orderQuantity))
                }
                assertThat(order.originPrice).isEqualTo(expectOriginPrice)
                assertThat(order.originPrice).isEqualTo(order.totalPrice)
            },
            DynamicTest.dynamicTest("할인 금액 계산") {
                val coupon = Coupon(1L, "쿠폰 A", Coupon.DiscountType.RATE, 10L, 30, 10L)

                // when
                order.calculateDiscountPrice(coupon)

                // then
                val expectDiscountPrice = expectOriginPrice * (
                    BigDecimal.valueOf(coupon.discount).divide(BigDecimal.valueOf(100))
                )
                assertThat(order.discountPrice).isEqualTo(expectDiscountPrice)

                val expectTotalPrice = expectOriginPrice - expectDiscountPrice
                assertThat(expectOriginPrice).isNotEqualTo(expectTotalPrice)
                assertThat(order.totalPrice).isEqualTo(expectTotalPrice)
            }
        )
    }

}