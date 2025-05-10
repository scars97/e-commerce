package com.ecommerce.adapter.`in`.controller

import com.ecommerce.adapter.`in`.dto.request.PointChargeRequest
import com.ecommerce.adapter.`in`.dto.response.PointResponse
import com.ecommerce.application.port.`in`.UserPointUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/points")
class PointController(
    private val userPointUseCase: UserPointUseCase
){

    @PostMapping("")
    fun pointCharge(@Valid @RequestBody request: PointChargeRequest): ResponseEntity<PointResponse> {
        val userPoint = userPointUseCase.pointRecharge(request.toCommand())

        return ResponseEntity.status(HttpStatus.CREATED).body(
            PointResponse(userPoint.id!!, userPoint.point)
        )
    }

    @GetMapping("/{userId}")
    fun getPoint(@PathVariable userId: Long): ResponseEntity<PointResponse> {
        val userPoint = userPointUseCase.getPoint(userId)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            PointResponse(userPoint.id!!, userPoint.point)
        )
    }

}