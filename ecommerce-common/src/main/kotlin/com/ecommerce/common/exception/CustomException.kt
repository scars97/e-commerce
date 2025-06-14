package com.ecommerce.common.exception

class CustomException(
    val errorCode: ErrorCode
): RuntimeException(errorCode.message)