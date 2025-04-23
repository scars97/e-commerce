package com.ecommerce.adapter.`in`.dto.response

import org.springframework.data.domain.Page

data class Pagination<T>(
    val currentPerPage: Int,
    val currentPage: Int,
    val totalPages: Int,
    val totalElements: Long,
    val content: List<T>
) {

    companion object {
        fun <T, R> of(data: Page<T>, mapper: (T) -> R): Pagination<R> {
            return Pagination(
                currentPerPage = data.size,
                currentPage = data.number,
                totalPages = data.totalPages,
                totalElements = data.totalElements,
                content = data.content.map(mapper)
            )
        }
    }

}
