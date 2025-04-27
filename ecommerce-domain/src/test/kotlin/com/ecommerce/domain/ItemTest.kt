package com.ecommerce.domain

import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import com.ecommerce.domain.item.Item
import com.ecommerce.domain.item.Stock
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ItemTest {

    @DisplayName("판매 중인 상품이 아닌 경우 예외가 발생한다.")
    @Test
    fun whenItemIsNotOnSale_thenThrowException() {
        // given
        val item = Item(
            1L,
            1L,
            "상품 A",
            BigDecimal.valueOf(5000L),
            "http://test.png",
            Item.ItemStatus.SOLD_OUT,
            Stock(1L, 20L)
        )

        // when & then
        assertThatThrownBy { item.isSelling() }
            .isInstanceOf(CustomException::class.java)
            .hasFieldOrPropertyWithValue("errorCode", ErrorCode.ITEM_IS_NOT_ON_SALE)
    }


    @DisplayName("재고 차감 요청 시 재고가 차감되고, 상품 재고가 0인 경우 SOLD_OUT 상태로 변경된다.")
    @Test
    fun whenRequestDeductStock_thenDeductStock_ifStockIsZeroThenItemStatusIsSoldOut() {
        // given
        val deductCount = 1L
        val item = Item(
            1L,
            1L,
            "상품 A",
            BigDecimal.valueOf(5000L),
            "http://test.png",
            Item.ItemStatus.SELLING,
            Stock(1L, 1L)
        )

        // when
        item.deductStock(deductCount)

        // then
        assertThat(item.stock.quantity).isZero()
        assertThat(item.status).isEqualTo(Item.ItemStatus.SOLD_OUT)
    }

    @DisplayName("상품 주문 수량이 현재 재고 수량보다 많은 경우 예외가 발생한다.")
    @Test
    fun whenRequestQuantityIsMoreThanStock_thenThrowException() {
        // given
        val requestQuantity = 5L
        val item = Item(
            1L,
            1L,
            "상품 A",
            BigDecimal.valueOf(5000L),
            "http://test.png",
            Item.ItemStatus.SELLING,
            Stock(1L, 1L)
        )

        // when & then
        assertThatThrownBy { item.deductStock(requestQuantity) }
            .isInstanceOf(CustomException::class.java)
            .hasFieldOrPropertyWithValue("errorCode", ErrorCode.OUT_OF_STOCK)
    }

}