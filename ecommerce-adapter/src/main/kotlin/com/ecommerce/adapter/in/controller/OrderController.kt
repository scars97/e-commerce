package com.ecommerce.adapter.`in`.controller

import com.ecommerce.adapter.`in`.dto.request.OrderRequest
import com.ecommerce.adapter.`in`.dto.response.OrderResponse
import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/orders")
class OrderController {

    @PostMapping("")
    fun placeOrder(@Valid @RequestBody request: OrderRequest): ResponseEntity<OrderResponse> {
        // 400 Bad Request
        if (request.couponId == 100L) throw CustomException(ErrorCode.INVALID_COUPON)

        request.orderItems.forEach {i ->
            // 404 Not Found
            if (i.itemId == 100L) throw CustomException(ErrorCode.ITEM_NOT_FOUND)
            // 409 Conflict
            if (i.quantity == 100L) throw CustomException(ErrorCode.OUT_OF_STOCK)
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(
            OrderResponse(1L, 1L, 1L, BigDecimal("3000"), BigDecimal("9000"), "ORDERED")
        )
    }

}