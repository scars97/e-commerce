package com.ecommerce.domain.order

import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import com.ecommerce.domain.coupon.Coupon
import com.ecommerce.domain.item.Item
import java.math.BigDecimal

class Order(
    val id: Long? = null,
    val couponId: Long? = null,
    val userId: Long,
    val orderItems: List<OrderItem> = listOf(),
    var originPrice: BigDecimal = BigDecimal.ZERO,
    var discountPrice: BigDecimal = BigDecimal.ZERO,
    var totalPrice: BigDecimal = BigDecimal.ZERO,
    var status: OrderStatus = OrderStatus.PENDING
) {

    enum class OrderStatus {
        PENDING, COMPLETED, CANCEL
    }

    fun calculateOriginPrice(items: List<Item>) {
        val quantityOfItem = this.orderItems.associate { it.itemId to BigDecimal.valueOf(it.quantity) }

        this.originPrice = items.sumOf {
             it.price * quantityOfItem[it.id]!!
        }

        this.totalPrice = this.originPrice
    }

    fun calculateDiscountPrice(coupon: Coupon) {
        this.discountPrice = coupon.calculateDiscount(this.originPrice)

        this.totalPrice = this.originPrice - this.discountPrice
    }

    fun complete(): Order {
        this.status = OrderStatus.COMPLETED
        return this
    }

    fun cancel(): Order {
        this.status = OrderStatus.CANCEL
        return this
    }

    fun ableToPaid() {
        if (this.status != OrderStatus.COMPLETED) {
            throw CustomException(ErrorCode.ORDER_CAN_NOT_BE_PAID)
        }
    }

}