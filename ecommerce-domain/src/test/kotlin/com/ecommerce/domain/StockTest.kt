package com.ecommerce.domain

import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import com.ecommerce.domain.item.Stock
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class StockTest {

    @DisplayName("상품 주문 수량이 현재 재고 수량보다 많은 경우 예외가 발생한다.")
    @Test
    fun whenRequestQuantityIsMoreThanStock_thenThrowException() {
        // given
        val requestQuantity = 5L
        val stock = Stock(1L, 1L, 1L)

        // when & then
        assertThatThrownBy { stock.deduct(requestQuantity) }
            .isInstanceOf(CustomException::class.java)
            .hasFieldOrPropertyWithValue("errorCode", ErrorCode.OUT_OF_STOCK)
    }

}