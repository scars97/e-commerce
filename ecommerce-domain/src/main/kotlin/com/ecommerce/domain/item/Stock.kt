package com.ecommerce.domain.item

import java.time.LocalDateTime

class Stock(
    val id: Long,
    val quantity: Long,
    val createAt: LocalDateTime,
    val modifiedAt: LocalDateTime
)