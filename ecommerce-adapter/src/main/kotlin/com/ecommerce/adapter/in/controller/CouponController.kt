package com.ecommerce.adapter.`in`.controller

import com.ecommerce.adapter.`in`.dto.request.FirstComeCouponRequest
import com.ecommerce.adapter.`in`.dto.response.CouponResponse
import com.ecommerce.application.port.`in`.CouponUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/coupons")
class CouponController(
    private val couponUseCase: CouponUseCase
) {

    private var requestCount= 0

    @PostMapping("/first-come")
    fun issuedFirstComeCoupon(@Valid @RequestBody request: FirstComeCouponRequest): ResponseEntity<CouponResponse> {
        val issuedCoupon = couponUseCase.issued(request.toCommand())

        return ResponseEntity.status(HttpStatus.CREATED).body(
            CouponResponse.of(issuedCoupon)
        )
    }

}