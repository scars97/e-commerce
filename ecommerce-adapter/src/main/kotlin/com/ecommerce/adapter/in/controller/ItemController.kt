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
    fun getPopularItems(
        @RequestParam(value = "period", defaultValue = "3") period: Long
    ): ResponseEntity<List<PopularItemResponse>> {
        val popularItems = itemUseCase.getPopularItemsOnTop10(period)

        return ResponseEntity.ok(
            popularItems.map { PopularItemResponse.of(it) }.toList()
        )
    }

}