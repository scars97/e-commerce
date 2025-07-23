package com.ecommerce.domain

import com.ecommerce.domain.coupon.Coupon
import com.ecommerce.domain.item.Item
import com.ecommerce.domain.item.Stock
import com.ecommerce.domain.order.Order
import com.ecommerce.domain.order.OrderItem
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
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

    @DisplayName("쿠폰 타입별 주문 금액 계산")
    @Test
    fun calculateOrderPriceOnCouponType() {
        // given
        val testCases = listOf(
            Coupon(1L, "10% 할인", Coupon.DiscountType.RATE, 10L, 30, 10L),
            Coupon(2L, "1,000원 할인", Coupon.DiscountType.AMOUNT, 1000L, 30, 10L),
            Coupon(0L, "할인 없음", Coupon.DiscountType.NONE, 0L, 0, 0L)
        )

        testCases.forEach { coupon ->
            // when
            val orderQuantity = 2L
            val orderItem = items.map { OrderItem(null, it.id, orderQuantity) }
            val order = Order(null, 1L, 1L, orderItem)

            order.calculatePrice(items, coupon)

            // then
            val expectOriginPrice = items.sumOf {
                it.price * (BigDecimal.valueOf(orderQuantity))
            }
            val expectDiscountPrice = when (coupon.type) {
                Coupon.DiscountType.RATE -> expectOriginPrice * BigDecimal.valueOf(coupon.discount).divide(BigDecimal.valueOf(100))
                Coupon.DiscountType.AMOUNT -> BigDecimal.valueOf(coupon.discount)
                Coupon.DiscountType.NONE -> BigDecimal.ZERO
            }
            val expectTotalPrice = expectOriginPrice.minus(expectDiscountPrice)

            assertThat(order.originPrice).isEqualTo(expectOriginPrice)
            assertThat(order.discountPrice).isEqualTo(expectDiscountPrice)
            assertThat(order.totalPrice).isEqualTo(expectTotalPrice)
        }
    }

}