package com.ecommerce.adapter.`in`.controller

import com.ecommerce.adapter.`in`.dto.request.ItemPageRequest
import com.ecommerce.adapter.`in`.dto.response.ItemResponse
import com.ecommerce.adapter.`in`.dto.response.PopularItemResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/items")
class ItemController {

    @GetMapping("")
    fun getItems(@Valid page: ItemPageRequest): ResponseEntity<List<ItemResponse>> {
        return ResponseEntity.ok(
            listOf(
                ItemResponse(1L, "오버핏 반팔티", BigDecimal("12000"), "http://test.png", "SELLING"),
                ItemResponse(2L, "오버핏 롱 슬리브", BigDecimal("15000"), "http://test.png", "SELLING")
            )
        )
    }

    @GetMapping("/popular")
    fun getPopularItems(): ResponseEntity<List<PopularItemResponse>> {
        return ResponseEntity.ok(
            listOf(
                PopularItemResponse(1, 25000L, 
                    ItemResponse(1L, "오버핏 반팔티", BigDecimal("12000"), "http://test.png", "SELLING")),
                PopularItemResponse(2, 20000L,
                    ItemResponse(2L, "오버핏 롱 슬리브", BigDecimal("15000"), "http://test.png", "SELLING")),
            )
        )
    }

}