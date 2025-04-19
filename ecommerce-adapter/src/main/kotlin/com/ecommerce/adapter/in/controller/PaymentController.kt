package com.ecommerce.adapter.`in`.controller

import com.ecommerce.adapter.`in`.dto.request.PaymentRequest
import com.ecommerce.adapter.`in`.dto.response.PaymentResponse
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
@RequestMapping("/api/payments")
class PaymentController {

    @PostMapping("")
    fun pay(@Valid @RequestBody request: PaymentRequest): ResponseEntity<PaymentResponse> {
        if (request.price > BigDecimal("12000")) throw CustomException(ErrorCode.INVALID_PRICE)

        if (request.orderId == 100L) throw CustomException(ErrorCode.ORDER_NOT_FOUND)

        if (request.price < BigDecimal("12000")) throw CustomException(ErrorCode.INSUFFICIENT_POINT)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            PaymentResponse(1L, 1L, 1L, BigDecimal("12000"), "PAID")
        )
    }

}