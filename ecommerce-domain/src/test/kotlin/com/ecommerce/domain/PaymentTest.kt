package com.ecommerce.domain

import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class PaymentTest {

    @DisplayName("요청한 금액이 실제 결제 금액과 맞지 않은 경우, 예외가 발생한다.")
    @Test
    fun whenRequestPriceIsNotEqualToPaymentPrice_thenThrowException() {
        // given
        val requestPrice = BigDecimal.valueOf(10000L)
        val paymentPrice = BigDecimal.valueOf(7000L)
        val payment = Payment(1L, 1L, paymentPrice)

        // when & then
        assertThatThrownBy { payment.priceEqualTo(requestPrice) }
            .isInstanceOf(CustomException::class.java)
            .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVALID_PRICE)
    }

}