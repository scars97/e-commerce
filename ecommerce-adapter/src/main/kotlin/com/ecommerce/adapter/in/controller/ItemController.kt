package com.ecommerce.adapter.`in`.controller

import com.ecommerce.adapter.`in`.dto.response.ItemResponse
import com.ecommerce.adapter.`in`.dto.response.Pagination
import com.ecommerce.adapter.`in`.dto.response.PopularItemResponse
import com.ecommerce.application.port.`in`.ItemUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/items")
class ItemController(
    private val itemUseCase: ItemUseCase
) {

    @GetMapping("")
    fun getItems(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
    ): ResponseEntity<Pagination<ItemResponse>> {
        val items = itemUseCase.getItems(page, size)

        return ResponseEntity.ok(
            Pagination.of(items) { ItemResponse.of(it) }
        )
    }

    @GetMapping("/popular")
    fun getPopularItems(): ResponseEntity<List<PopularItemResponse>> {
        return ResponseEntity.ok(
            listOf(
                PopularItemResponse(1, 25000L, 
                    ItemResponse(1L, 1L, "오버핏 반팔티", BigDecimal("12000"), "http://test.png", "SELLING")),
                PopularItemResponse(2, 20000L,
                    ItemResponse(2L, 1L, "오버핏 롱 슬리브", BigDecimal("15000"), "http://test.png", "SELLING")),
            )
        )
    }

}