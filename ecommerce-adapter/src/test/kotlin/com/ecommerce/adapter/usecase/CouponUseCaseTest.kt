package com.ecommerce.adapter.usecase

import com.ecommerce.adapter.config.IntegrateTestSupport
import com.ecommerce.adapter.out.persistence.entity.CouponEntity
import com.ecommerce.adapter.out.persistence.entity.UserEntity
import com.ecommerce.adapter.out.persistence.repository.CouponJpaRepository
import com.ecommerce.adapter.out.persistence.repository.UserJpaRepository
import com.ecommerce.application.dto.CouponCommand
import com.ecommerce.application.port.`in`.CouponUseCase
import com.ecommerce.domain.coupon.Coupon
import com.ecommerce.domain.coupon.UserCoupon
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal

class CouponUseCaseTest @Autowired constructor(
    private val sut: CouponUseCase,
    private val userRepository: UserJpaRepository,
    private val couponRepository: CouponJpaRepository
): IntegrateTestSupport() {

    private val couponQuantity = 50L

    @BeforeEach
    fun setUp() {
        userRepository.save(UserEntity(null, "성현", BigDecimal.ZERO))
        couponRepository.save(CouponEntity(null, "선착순 쿠폰", Coupon.DiscountType.RATE, 10, 30, couponQuantity))
    }

    @Test
    fun issuedCoupon() {
        // given
        val command = CouponCommand(1L, 1L)

        // when
        val result = sut.issued(command)

        // then
        assertThat(result).extracting("id", "coupon.title", "coupon.quantity", "status")
            .containsExactly(1L, "선착순 쿠폰", couponQuantity - 1L, UserCoupon.UserCouponStatus.AVAILABLE)
        assertThat(result.issuedAt).isNotNull()
        assertThat(result.expireAt).isNotNull()
        assertThat(result.usedAt).isNull()

    }

}