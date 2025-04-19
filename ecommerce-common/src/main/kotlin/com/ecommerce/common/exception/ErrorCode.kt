package com.ecommerce.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {

    // 상품
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다."),

    // 주문
    OUT_OF_STOCK(HttpStatus.CONFLICT, "요청하신 상품의 재고가 부족하여 주문할 수 없습니다."),

    // 쿠폰
    INVALID_COUPON(HttpStatus.BAD_REQUEST, "만료되었거나 사용할 수 없는 쿠폰입니다."),

}