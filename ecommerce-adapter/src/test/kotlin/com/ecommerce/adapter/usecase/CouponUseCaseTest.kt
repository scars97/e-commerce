package com.ecommerce.adapter.usecase

import com.ecommerce.adapter.config.IntegrateTestSupport
import com.ecommerce.adapter.out.persistence.entity.CouponEntity
import com.ecommerce.adapter.out.persistence.entity.UserEntity
import com.ecommerce.adapter.out.persistence.repository.CouponJpaRepository
import com.ecommerce.adapter.out.persistence.repository.UserJpaRepository
import com.ecommerce.application.dto.CouponCommand
import com.ecommerce.application.port.`in`.CouponUseCase
import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import com.ecommerce.domain.coupon.Coupon
import com.ecommerce.domain.coupon.UserCoupon
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicInteger

class CouponUseCaseTest @Autowired constructor(
    private val sut: CouponUseCase,
    private val userRepository: UserJpaRepository,
    private val couponRepository: CouponJpaRepository
): IntegrateTestSupport() {

    private val couponQuantity = 50L

    @DisplayName("쿠폰 발급 시나리오")
    @TestFactory
    fun issuedCoupon(): List<DynamicTest> {
        // given
        userRepository.save(UserEntity(null, "성현", BigDecimal.ZERO))
        couponRepository.save(CouponEntity(null, "선착순 쿠폰", Coupon.DiscountType.RATE, 10, 30, couponQuantity))
        
        val command = CouponCommand(1L, 1L)

        return listOf(
            DynamicTest.dynamicTest("쿠폰 정상 발급") {
                // when
                val result = sut.issued(command)

                // then
                assertThat(result).extracting("id", "coupon.title", "coupon.quantity", "status")
                    .containsExactly(1L, "선착순 쿠폰", couponQuantity - 1L, UserCoupon.UserCouponStatus.AVAILABLE)
                assertThat(result.issuedAt).isNotNull()
                assertThat(result.expireAt).isNotNull()
                assertThat(result.usedAt).isNull()
            },
            DynamicTest.dynamicTest("발급된 쿠폰인 경우, 예외 발생") {
                // when & then
                assertThatThrownBy { sut.issued(command) }
                    .isInstanceOf(CustomException::class.java)
                    .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COUPON_ALREADY_ISSUED)
            }
        )
    }

    @DisplayName("쿠폰 수량이 50개인 경우, 50명은 발급에 성공하지만 1명은 발급에 실패한다.")
    @Test
    fun whenCouponQuantityIs50_then50UserWillSuccessBut1UserWillFail() {
        // given
        val totalUser = 51
        for (i in 1 .. totalUser) {
            val username = "user$i"
            userRepository.save(
                UserEntity(null, username, BigDecimal.ZERO)
            )
        }
        couponRepository.save(CouponEntity(null, "선착순 쿠폰", Coupon.DiscountType.RATE, 10, 30, couponQuantity))

        val successCount = AtomicInteger(0)
        val failureCount = AtomicInteger(0)

        // when
        val tasks = (1 .. totalUser).map { userId ->
            CompletableFuture.runAsync{
                try {
                    sut.issued(CouponCommand(1L, userId.toLong()))
                    successCount.incrementAndGet()
                } catch (e: Exception) {
                    failureCount.incrementAndGet()
                }
            }
        }

        CompletableFuture.allOf(*tasks.toTypedArray()).join()

        // then
        assertThat(successCount.get()).isEqualTo(50)
        assertThat(failureCount.get()).isOne()
    }

}