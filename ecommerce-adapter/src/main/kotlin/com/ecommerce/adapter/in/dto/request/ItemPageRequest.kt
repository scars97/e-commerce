package com.ecommerce.adapter.`in`.dto.request

import jakarta.validation.constraints.Positive

data class ItemPageRequest(
    @field:Positive(message = "페이지 크기는 0보다 커야 합니다.")
    val pageSize: Int?,
    @field:Positive(message = "페이지 번호는 0보다 커야 합니다.")
    val pageNumber: Int?,
)
