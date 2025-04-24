package com.ecommerce.domain.item

import java.time.LocalDateTime

class Category(
    val id: Long,
    val name: String,
    val createAt: LocalDateTime,
    val modifiedAt: LocalDateTime
)