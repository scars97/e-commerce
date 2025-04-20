package com.ecommerce.adapter.`in`.controller

import com.ecommerce.adapter.`in`.dto.request.PointChargeRequest
import com.ecommerce.adapter.`in`.dto.response.PointResponse
import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/points")
class PointController {

    @PostMapping("")
    fun pointCharge(@Valid @RequestBody request: PointChargeRequest): ResponseEntity<PointResponse> {
        if (request.userId == 100L) throw CustomException(ErrorCode.USER_NOT_FOUND)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            PointResponse(1L, BigDecimal("15000"))
        )
    }

    @GetMapping("/{userId}")
    fun getPoint(@PathVariable userId: Long): ResponseEntity<PointResponse> {
        if (userId == 100L) throw CustomException(ErrorCode.USER_NOT_FOUND)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            PointResponse(1L, BigDecimal("15000"))
        )
    }

}