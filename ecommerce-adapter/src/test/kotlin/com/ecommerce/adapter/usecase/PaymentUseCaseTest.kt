package com.ecommerce.adapter.usecase

import com.ecommerce.adapter.config.IntegrateTestSupport
import com.ecommerce.adapter.fixture.OrderFixture
import com.ecommerce.adapter.fixture.UserFixture
import com.ecommerce.adapter.out.persistence.repository.OrderJpaRepository
import com.ecommerce.adapter.out.persistence.repository.PointHistoryJpaRepository
import com.ecommerce.adapter.out.persistence.repository.UserJpaRepository
import com.ecommerce.application.dto.PaymentCommand
import com.ecommerce.application.port.`in`.PaymentUseCase
import com.ecommerce.domain.payment.Payment
import com.ecommerce.domain.user.PointHistory
import com.ecommerce.domain.order.Order
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal

class PaymentUseCaseTest @Autowired constructor(
    private val sut: PaymentUseCase,
    private val orderFixture: OrderFixture,
    private val userFixture: UserFixture,
    private val userRepository: UserJpaRepository,
    private val pointHistoryRepository: PointHistoryJpaRepository,
    private val orderRepository: OrderJpaRepository
): IntegrateTestSupport() {

    private lateinit var order: Order
    private lateinit var paymentAmount: BigDecimal

    @BeforeEach
    fun setUp() {
        order = orderFixture.placeOrder(OrderFixture.OrderFixtureStatus.SINGLE)
        paymentAmount = order.totalPrice
    }

    @Test
    fun pay() {
        // given
        val command = PaymentCommand(order.userId, order.id!!, paymentAmount)

        // when
        val result = sut.pay(command)

        // then
        verifyPayment(result)
        verifyRemainingPoint(result)
        verifyPointHistory()
    }

    private fun verifyPayment(result: Payment) {
        assertThat(result)
            .extracting("id", "orderId", "userId", "status")
            .containsExactly(1L, 1L, 1L, Payment.PaymentStatus.PAID)
        assertThat(result.price.compareTo(paymentAmount)).isZero()
    }

    private fun verifyRemainingPoint(result: Payment) {
        val findUser = userRepository.findById(result.userId).get()
        val expectPoint = userFixture.getUser().point - paymentAmount
        assertThat(findUser.point.compareTo(expectPoint)).isZero()
    }

    private fun verifyPointHistory() {
        val findPointHistory = pointHistoryRepository.findAll()[0]
        assertThat(findPointHistory)
            .extracting("id", "userId", "status")
            .containsExactly(1L, 1L, PointHistory.PointHistoryStatus.USED)
        assertThat(findPointHistory.amount.compareTo(paymentAmount)).isZero()
    }

}