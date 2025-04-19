package com.ecommerce.adapter.`in`.dto.request

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class PaymentRequest(
    @field:NotNull(message = "사용자 ID는 필수 값 입니다.")
    @field:Positive(message = "사용자 ID 값은 0보다 커야 합니다.")
    val userId: Long,
    @field:NotNull(message = "주문 ID는 필수 값 입니다.")
    @field:Positive(message = "주문 ID 값은 0보다 커야 합니다.")
    val orderId: Long,
    @field:NotNull(message = "결제 금액은 필수 값 입니다.")
    @field:Positive(message = "결제 금액은 0보다 커야 합니다.")
    val price: BigDecimal
)
