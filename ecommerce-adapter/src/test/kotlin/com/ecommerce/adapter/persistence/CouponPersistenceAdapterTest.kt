package com.ecommerce.adapter.persistence

import com.ecommerce.adapter.config.IntegrateTestSupport
import com.ecommerce.application.port.out.CouponPort
import com.ecommerce.domain.coupon.Coupon
import com.ecommerce.domain.coupon.UserCoupon
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

class CouponPersistenceAdapterTest @Autowired constructor(
    private val sut: CouponPort
): IntegrateTestSupport() {

    @TestFactory
    fun commandCoupon(): List<DynamicTest> {
        return listOf(
            DynamicTest.dynamicTest("쿠폰 생성") {
                // given
                val coupon = Coupon(null, "쿠폰 A", Coupon.DiscountType.RATE, 30, 30, 100L)

                // when
                val result = sut.commandCoupon(coupon)

                // then
                assertThat(result).extracting("id", "quantity")
                    .containsExactly(1L, 100L)
            },
            DynamicTest.dynamicTest("쿠폰 수량 업데이트") {
                // given
                val findCoupon = sut.findCouponById(1L)

                // when
                val deductCoupon = findCoupon.deduct()
                val result = sut.commandCoupon(deductCoupon)

                // then
                assertThat(result).extracting("id", "quantity")
                    .containsExactly(1L, 99L)
            },
        )
    }

    @Test
    fun commandUserCoupon() {
        // given
        val coupon = Coupon(null, "쿠폰 A", Coupon.DiscountType.RATE, 30, 30, 100L)
        val savedCoupon = sut.commandCoupon(coupon)

        val userCoupon = UserCoupon(null, 1L, savedCoupon, UserCoupon.UserCouponStatus.AVAILABLE, LocalDateTime.now(),
            LocalDateTime.now().plusDays(savedCoupon.expirationDay.toLong()), null)

        // when
        val result = sut.commandUserCoupon(userCoupon)

        // then
        assertThat(result.id).isOne()
        assertThat(result.coupon.id).isEqualTo(savedCoupon.id)
    }

}