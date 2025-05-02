package com.ecommerce.adapter.`in`.controller

import com.ecommerce.adapter.`in`.dto.request.PaymentRequest
import com.ecommerce.adapter.`in`.dto.response.PaymentResponse
import com.ecommerce.application.port.`in`.PaymentUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/payments")
class PaymentController(
    private val paymentUseCase: PaymentUseCase
) {

    @PostMapping("")
    fun pay(@Valid @RequestBody request: PaymentRequest): ResponseEntity<PaymentResponse> {
        val payment = paymentUseCase.pay(request.toCommand())

        return ResponseEntity.status(HttpStatus.CREATED).body(
            PaymentResponse.of(payment)
        )
    }

}