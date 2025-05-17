package com.ecommerce.domain

import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import com.ecommerce.domain.item.Item
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
            Item.ItemStatus.SOLD_OUT
        )

        // when & then
        assertThatThrownBy { item.isSelling() }
            .isInstanceOf(CustomException::class.java)
            .hasFieldOrPropertyWithValue("errorCode", ErrorCode.ITEM_IS_NOT_ON_SALE)
    }


    @DisplayName("재고가 모두 차감된 경우, 상품이 SOLD_OUT 상태로 변경된다.")
    @Test
    fun whenStockIsZero_thenItemStatusIsSoldOut() {
        // given
        val item = Item(
            1L,
            1L,
            "상품 A",
            BigDecimal.valueOf(5000L),
            "http://test.png",
            Item.ItemStatus.SELLING
        )

        // when
        item.soldOut()

        // then
        assertThat(item.status).isEqualTo(Item.ItemStatus.SOLD_OUT)
    }

}