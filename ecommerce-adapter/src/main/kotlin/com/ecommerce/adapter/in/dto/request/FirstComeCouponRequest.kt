package com.ecommerce.adapter.`in`.dto.request

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class FirstComeCouponRequest(
    @field:NotNull(message = "사용자 ID는 필수 값 입니다.")
    @field:Positive(message = "사용자 ID 값은 0보다 커야 합니다.")
    val userId: Long
)
