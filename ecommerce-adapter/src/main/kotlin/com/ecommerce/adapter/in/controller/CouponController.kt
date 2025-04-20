package com.ecommerce.adapter.`in`.controller

import com.ecommerce.adapter.`in`.dto.request.FirstComeCouponRequest
import com.ecommerce.adapter.`in`.dto.response.CouponResponse
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
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/coupons")
class CouponController {

    private var requestCount= 0

    @PostMapping("/first-come")
    fun issuedFirstComeCoupon(@Valid @RequestBody request: FirstComeCouponRequest): ResponseEntity<CouponResponse> {
        if (request.userId == 100L) throw CustomException(ErrorCode.USER_NOT_FOUND)

        if (requestCount == 5) {
            requestCount = 0
            throw CustomException(ErrorCode.COUPONS_ARE_EXHAUSTED)
        }
        requestCount++

        return ResponseEntity.status(HttpStatus.CREATED).body(
            CouponResponse(
                1L,
                "선착순 쿠폰",
                "RATE",
                BigDecimal("30"),
                30,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30)
            )
        )
    }

}