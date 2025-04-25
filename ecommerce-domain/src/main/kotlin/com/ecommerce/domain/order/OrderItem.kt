package com.ecommerce.domain.order

class OrderItem(
    val id: Long,
    val itemId: Long,
    val quantity: Long
) {

    constructor(itemId: Long, quantity: Long): this(0, itemId, quantity)

}