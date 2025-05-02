package com.ecommerce.adapter.`in`.controller

import com.ecommerce.adapter.`in`.dto.request.OrderRequest
import com.ecommerce.adapter.`in`.dto.response.OrderResponse
import com.ecommerce.application.port.`in`.OrderUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/orders")
class OrderController(
    private val orderUseCase: OrderUseCase
) {

    @PostMapping("")
    fun placeOrder(@Valid @RequestBody request: OrderRequest): ResponseEntity<OrderResponse> {
        val order = orderUseCase.placeOrder(request.toOrderCommand())

        return ResponseEntity.status(HttpStatus.CREATED).body(
            OrderResponse.of(order)
        )
    }

}