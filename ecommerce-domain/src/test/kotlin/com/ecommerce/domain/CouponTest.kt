package com.ecommerce.domain

import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import com.ecommerce.domain.coupon.Coupon
import com.ecommerce.domain.coupon.UserCoupon
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.stream.Stream

class CouponTest {

    companion object {
        @JvmStatic
        fun discountTestCases(): Stream<TestCase> {
            return Stream.of(
                TestCase(
                    Coupon(1L, "쿠폰 A", Coupon.DiscountType.RATE, 10L, 30),
                    BigDecimal.valueOf(1000L)
                ),
                TestCase(
                    Coupon(2L, "쿠폰 B", Coupon.DiscountType.AMOUNT, 3000L, 30),
                    BigDecimal.valueOf(3000L)
                )
            )
        }
    }

    data class TestCase(
        val coupon: Coupon,
        val expected: BigDecimal
    )

    @DisplayName("할인 종류에 따른 할인 금액 계산")
    @ParameterizedTest
    @MethodSource("discountTestCases")
    fun calculateDisCount(testCase: TestCase) {
        // given
        val price = BigDecimal.valueOf(10000L)
        val coupon = testCase.coupon

        // when
        val discount = coupon.calculateDiscount(price)

        // then
        assertThat(discount.compareTo(testCase.expected)).isZero()
    }

    @DisplayName("쿠폰 검증 시나리오")
    @TestFactory
    fun validateCoupon(): List<DynamicTest> {
        val coupon = Coupon(1L, "쿠폰 A", Coupon.DiscountType.RATE, 10L, 30)

        return listOf(
            DynamicTest.dynamicTest("사용 가능 상태가 아닌 경우 예외 발생") {
                val userCoupon = UserCoupon(1L, 1L, coupon, UserCoupon.UserCouponStatus.USED, LocalDateTime.now().plusDays(1), LocalDateTime.now())
                assertThatThrownBy { userCoupon.validate() }
                    .isInstanceOf(CustomException::class.java)
                    .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVALID_COUPON)
            },
            DynamicTest.dynamicTest("사용 가능 상태이더라도 만료 시간이 지난 경우 예외 발생") {
                val userCoupon = UserCoupon(1L, 1L, coupon, UserCoupon.UserCouponStatus.AVAILABLE, LocalDateTime.now().minusDays(1), null)
                assertThatThrownBy { userCoupon.validate() }
                    .isInstanceOf(CustomException::class.java)
                    .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVALID_COUPON)
            }
        )
    }

    @DisplayName("쿠폰 사용 요청 시 쿠폰 상태가 '사용'으로 변경되고 사용 시간이 입력된다.")
    @Test
    fun whenRequestUse_thenStatusIsUsedAndInputUseTime() {
        // given
        val coupon = Coupon(1L, "쿠폰 A", Coupon.DiscountType.RATE, 10L, 30)
        val userCoupon = UserCoupon(1L, 1L, coupon, UserCoupon.UserCouponStatus.AVAILABLE, LocalDateTime.now().plusDays(1), null)

        // when
        val result = userCoupon.use()

        // then
        assertThat(result.status).isEqualTo(UserCoupon.UserCouponStatus.USED)
        assertThat(result.usedAt).isNotNull()
    }

}