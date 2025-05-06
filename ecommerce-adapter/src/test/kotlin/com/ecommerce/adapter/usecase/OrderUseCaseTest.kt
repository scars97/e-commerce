package com.ecommerce.adapter.usecase

import com.ecommerce.adapter.config.IntegrateTestSupport
import com.ecommerce.adapter.out.persistence.entity.*
import com.ecommerce.adapter.out.persistence.repository.CouponJpaRepository
import com.ecommerce.adapter.out.persistence.repository.ItemJpaRepository
import com.ecommerce.adapter.out.persistence.repository.UserCouponJpaRepository
import com.ecommerce.adapter.out.persistence.repository.UserJpaRepository
import com.ecommerce.application.dto.OrderCommand
import com.ecommerce.application.port.`in`.OrderUseCase
import com.ecommerce.domain.coupon.Coupon
import com.ecommerce.domain.coupon.UserCoupon
import com.ecommerce.domain.item.Item
import com.ecommerce.domain.order.Order
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal
import java.time.LocalDateTime

class OrderUseCaseTest @Autowired constructor(
    private val sut: OrderUseCase,
    private val userRepository: UserJpaRepository,
    private val couponRepository: CouponJpaRepository,
    private val userCouponRepository: UserCouponJpaRepository,
    private val itemRepository: ItemJpaRepository
): IntegrateTestSupport() {

    private lateinit var user: UserEntity
    private lateinit var coupon: CouponEntity
    private lateinit var userCoupon: UserCouponEntity
    private lateinit var items: List<ItemEntity>

    @BeforeEach
    fun setUp() {
        user = userRepository.save(UserEntity(null, "성현", BigDecimal.ZERO))
        coupon = couponRepository.save(CouponEntity(null, "선착순 쿠폰", Coupon.DiscountType.RATE, 10, 30, 50))
        userCoupon = userCouponRepository.save(
            UserCouponEntity(null, user.id!!, coupon, UserCoupon.UserCouponStatus.AVAILABLE, LocalDateTime.now(), LocalDateTime.now().plusDays(30), null)
        )
        items = itemRepository.saveAll(
            listOf(
                ItemEntity(null, 1L, "상품 A", BigDecimal.valueOf(10000L),"http://test.png", Item.ItemStatus.SELLING, StockEntity(null, 10L)),
                ItemEntity(null, 1L, "상품 B", BigDecimal.valueOf(7000L),"http://test.png", Item.ItemStatus.SELLING, StockEntity(null, 7L)),
                ItemEntity(null, 1L, "상품 C", BigDecimal.valueOf(5000L),"http://test.png", Item.ItemStatus.SELLING, StockEntity(null, 5L)),
            )
        )
    }

    @Test
    fun placeOrder() {
        // given
        val deductStock = 2L
        val command = OrderCommand(
            userId = 1L,
            couponId = 1L,
            orderItems = listOf(
                OrderCommand.OrderItemCommand(1L, deductStock),
                OrderCommand.OrderItemCommand(2L, deductStock),
                OrderCommand.OrderItemCommand(3L, deductStock)
            )
        )

        // when
        val result = sut.placeOrder(command)

        // then
        assertThat(result)
            .extracting("id", "couponId", "userId", "status")
            .containsExactly(1L, 1L, 1L, Order.OrderStatus.ORDERED)
        verifyOrderPrice(command, result)
        verifyRemainingStock(deductStock)
        verifyUserCoupon()
    }

    private fun verifyUserCoupon() {
        val findUserCoupon = userCouponRepository.findById(userCoupon.id!!).get()
        assertThat(findUserCoupon.status).isEqualTo(UserCoupon.UserCouponStatus.USED)
        assertThat(findUserCoupon.usedAt).isNotNull()
    }

    private fun verifyRemainingStock(deductStock: Long) {
        val findItems = itemRepository.findAll()
        val originStockOfItem = items.associate { it.id to it.stock }

        findItems.forEach {
            val actualQuantity = it.stock.quantity
            val expectQuantity = originStockOfItem[it.id]!!.quantity - deductStock

            assertThat(actualQuantity).isEqualTo(expectQuantity)
        }
    }

    private fun verifyOrderPrice(command: OrderCommand, result: Order) {
        val quantityOfItem = command.orderItems.associate { it.itemId to it.quantity }
        val expectOriginPrice = items.sumOf {
            it.price * BigDecimal.valueOf(quantityOfItem[it.id]!!)
        }
        val expectDiscountPrice = expectOriginPrice * BigDecimal.valueOf(coupon.discount).divide(BigDecimal.valueOf(100))
        val expectTotalPrice = expectOriginPrice - expectDiscountPrice

        assertThat(result.originPrice.compareTo(expectOriginPrice)).isZero()
        assertThat(result.discountPrice.compareTo(expectDiscountPrice)).isZero()
        assertThat(result.totalPrice.compareTo(expectTotalPrice)).isZero()
    }

}