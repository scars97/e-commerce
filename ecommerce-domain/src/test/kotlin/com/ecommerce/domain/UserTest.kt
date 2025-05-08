package com.ecommerce.domain

import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import com.ecommerce.domain.user.User
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
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


    @DisplayName("포인트 사용 시나리오")
    @TestFactory
    fun userPointUsed(): List<DynamicTest> {
        // given
        val usePoint = BigDecimal.valueOf(1000L)
        val user = User(1L, "username", BigDecimal.valueOf(1000), LocalDateTime.now(), LocalDateTime.now())

        return listOf(
            DynamicTest.dynamicTest("포인트 정상 차감") {
                // when
                user.pointUse(usePoint)

                // then
                val expectPoint = BigDecimal.ZERO
                assertThat(user.point.compareTo(expectPoint)).isZero()
            },
            DynamicTest.dynamicTest("잔액 부족 시 예외 발생") {
                // when & then
                assertThatThrownBy { user.pointUse(usePoint) }
                    .isInstanceOf(CustomException::class.java)
                    .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INSUFFICIENT_POINT)
            }
        )
    }

}