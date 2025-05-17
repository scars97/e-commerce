package com.ecommerce.adapter.fixture

import com.ecommerce.adapter.out.persistence.entity.*
import com.ecommerce.application.dto.OrderCommand
import com.ecommerce.application.port.`in`.OrderUseCase
import com.ecommerce.domain.coupon.Coupon
import com.ecommerce.domain.order.Order
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class OrderFixture(
    private val orderUseCase: OrderUseCase,
    private val userFixture: UserFixture,
    private val couponFixture: CouponFixture,
    private val itemFixture: ItemFixture
) {

    enum class OrderFixtureStatus {
        SINGLE, BULK
    }

    fun execute() {
        val user = userFixture.createSingleUser(BigDecimal.valueOf(1_000_000L))
        val coupon = couponFixture.createCoupon(Coupon.DiscountType.RATE, 10, 50)
        couponFixture.createAvailableUserCoupon(user.id!!, coupon)
        itemFixture.createItemsAndStocks(50L)
        itemFixture.getStocks()
    }

    fun placeOrder(status: OrderFixtureStatus): Order {
        this.execute()

        val command = when (status) {
            OrderFixtureStatus.SINGLE -> single()
            OrderFixtureStatus.BULK -> bulk()
        }

        return orderUseCase.placeOrder(command)
    }

    private fun single(): OrderCommand {
        return OrderCommand(
            userId = 1L,
            couponId = null,
            orderItems = listOf(
                OrderCommand.OrderItemCommand(1L, 2L)
            )
        )
    }

    private fun bulk(): OrderCommand {
        return OrderCommand(
            userId = 1L,
            couponId = null,
            orderItems = listOf(
                OrderCommand.OrderItemCommand(1L, 15L),
                OrderCommand.OrderItemCommand(2L, 18L),
                OrderCommand.OrderItemCommand(3L, 10L),
                OrderCommand.OrderItemCommand(4L, 12L),
                OrderCommand.OrderItemCommand(5L, 20L),
            )
        )
    }

}