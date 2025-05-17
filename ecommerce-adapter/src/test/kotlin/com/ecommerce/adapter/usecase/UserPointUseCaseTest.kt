package com.ecommerce.adapter.usecase

import com.ecommerce.adapter.config.IntegrateTestSupport
import com.ecommerce.adapter.fixture.UserFixture
import com.ecommerce.adapter.out.persistence.repository.PointHistoryJpaRepository
import com.ecommerce.application.dto.PointCommand
import com.ecommerce.application.port.`in`.UserPointUseCase
import com.ecommerce.domain.user.PointHistory
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal

class UserPointUseCaseTest @Autowired constructor(
    private val sut: UserPointUseCase,
    private val userFixture: UserFixture,
    private val historyJpaRepository: PointHistoryJpaRepository
): IntegrateTestSupport() {

    @BeforeEach
    fun setUp() {
        userFixture.createSingleUser()
    }

    @DisplayName("포인트 충전 요청 시, 사용자 포인트가 충전되고 충전 내역이 저장된다.")
    @Test
    fun pointRecharge() {
        // given
        val command = PointCommand(1L, BigDecimal.valueOf(1000L))

        // when
        val result = sut.pointRecharge(command)

        // then
        assertThat(result.id).isEqualTo(1L)
        assertThat(result.point.compareTo(BigDecimal.valueOf(1000L))).isZero()

        val history = historyJpaRepository.findById(1L).get()
        assertThat(history.userId).isEqualTo(1L)
        assertThat(history.status).isEqualTo(PointHistory.PointHistoryStatus.RECHARGE)
        assertThat(history.amount.compareTo(BigDecimal.valueOf(1000L))).isZero()
    }

}