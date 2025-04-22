package com.ecommerce.adapter

import com.ecommerce.adapter.config.IntegrateTestSupport
import com.ecommerce.adapter.out.persistence.entity.UserEntity
import com.ecommerce.adapter.out.persistence.repository.PointHistoryJapRepository
import com.ecommerce.adapter.out.persistence.repository.UserJpaRepository
import com.ecommerce.application.dto.PointCommand
import com.ecommerce.application.port.`in`.UserPointUseCase
import com.ecommerce.domain.PointHistory
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal

class UserPointTest @Autowired constructor(
    private val sut: UserPointUseCase,
    private val userJpaRepository: UserJpaRepository,
    private val historyJapRepository: PointHistoryJapRepository
): IntegrateTestSupport() {

    @BeforeEach
    fun setUp() {
        userJpaRepository.save(
            UserEntity("test")
        )
    }

    @DisplayName("포인트 충전 요청 시, 사용자 포인트가 충전되고 충전 내역이 저장된다.")
    @Test
    fun pointRecharge() {
        // given
        val command = PointCommand(1L, BigDecimal.valueOf(1000L))

        // when
        val result = sut.pointRecharge(command)

        // then
        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.remainingPoint.compareTo(BigDecimal.valueOf(1000L))).isZero()

        val history = historyJapRepository.findById(1L).get()
        assertThat(history.userId).isEqualTo(1L)
        assertThat(history.status).isEqualTo(PointHistory.PointHistoryStatus.RECHARGE)
        assertThat(history.amount.compareTo(BigDecimal.valueOf(1000L))).isZero()
    }

}