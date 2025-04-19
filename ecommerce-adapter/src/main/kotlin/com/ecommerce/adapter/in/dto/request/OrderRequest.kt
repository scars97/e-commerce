package com.ecommerce.adapter.`in`.dto.request

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class OrderRequest(
    @field:NotNull(message = "사용자 ID는 필수 값 입니다.")
    @field:Positive(message = "사용자 ID 값은 0보다 커야 합니다.")
    val userId: Long,
    @field:Positive(message = "쿠폰 ID 값은 0보다 커야 합니다.")
    val couponId: Long?,
    @field:NotEmpty(message = "주문 상품 목록은 비어 있을 수 없습니다.")
    @field:Valid
    val orderItems: List<OrderItemRequest>
) {

    data class OrderItemRequest(
        @field:NotNull(message = "상품 ID는 필수 값 입니다.")
        @field:Positive(message = "상품 ID 값은 0보다 커야 합니다.")
        val itemId: Long,
        @field:NotNull(message = "주문 수량은 필수 값 입니다.")
        @field:Positive(message = "주문 수량은 0보다 커야 합니다.")
        val quantity: Long
    )

}
