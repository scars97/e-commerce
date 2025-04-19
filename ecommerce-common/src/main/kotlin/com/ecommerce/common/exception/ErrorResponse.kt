package com.ecommerce.common.exception

import org.springframework.http.ResponseEntity

data class ErrorResponse(
    val errorCode: String,
    val message: String
) {

    companion object {
        fun of(errorCode: ErrorCode): ResponseEntity<ErrorResponse> {
            return ResponseEntity.status(errorCode.status)
                .body(ErrorResponse(errorCode.name, errorCode.message))
        }
    }

}
