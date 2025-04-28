package com.ecommerce.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {

    // 상품
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다."),
    ITEM_IS_NOT_ON_SALE(HttpStatus.BAD_REQUEST, "판매 중인 상품이 아닙니다."),

    // 주문
    OUT_OF_STOCK(HttpStatus.CONFLICT, "요청하신 상품의 재고가 부족하여 주문할 수 없습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문 내역을 찾을 수 없습니다."),

    // 쿠폰
    COUPON_NOT_FOUND(HttpStatus.NOT_FOUND, "쿠폰을 찾을 수 없습니다."),
    INVALID_COUPON(HttpStatus.BAD_REQUEST, "만료되었거나 사용할 수 없는 쿠폰입니다."),
    COUPONS_ARE_EXHAUSTED(HttpStatus.CONFLICT, "쿠폰이 모두 소진되었습니다."),

    // 결제
    INVALID_PRICE(HttpStatus.BAD_REQUEST, "결제 금액이 맞지 않습니다."),
    INSUFFICIENT_POINT(HttpStatus.UNPROCESSABLE_ENTITY, "잔액이 부족합니다."),

    // 사용자
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),

    // 공통
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.")

}