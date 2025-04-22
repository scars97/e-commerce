package com.ecommerce.domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime

class UserTest {

    @DisplayName("사용자 포인트가 정상적으로 충전된다.")
    @Test
    fun userPointRecharge() {
        // given
        val rechargePoint = BigDecimal.valueOf(1000L)
        val user = User(1L, "username", BigDecimal.ZERO, LocalDateTime.now(), LocalDateTime.now())

        // when
        user.pointRecharge(rechargePoint)

        // then
        assertThat(user.point).isEqualTo(rechargePoint)
    }

}