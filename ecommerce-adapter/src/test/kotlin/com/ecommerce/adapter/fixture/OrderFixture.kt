package com.ecommerce.adapter.fixture

import com.ecommerce.adapter.out.persistence.entity.*
import com.ecommerce.adapter.out.persistence.repository.CouponJpaRepository
import com.ecommerce.adapter.out.persistence.repository.ItemJpaRepository
import com.ecommerce.adapter.out.persistence.repository.UserCouponJpaRepository
import com.ecommerce.adapter.out.persistence.repository.UserJpaRepository
import com.ecommerce.domain.coupon.Coupon
import com.ecommerce.domain.coupon.UserCoupon
import com.ecommerce.domain.item.Item
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime

@Component
class OrderFixture(
    private val userRepository: UserJpaRepository,
    private val couponRepository: CouponJpaRepository,
    private val userCouponRepository: UserCouponJpaRepository,
    private val itemRepository: ItemJpaRepository
) {

    private lateinit var user: UserEntity
    private lateinit var coupon: CouponEntity
    private lateinit var userCoupon: UserCouponEntity
    private lateinit var items: List<ItemEntity>

    fun execute() {
        user = userRepository.save(UserEntity(null, "성현", BigDecimal.ZERO))
        coupon = couponRepository.save(CouponEntity(null, "선착순 쿠폰", Coupon.DiscountType.RATE, 10, 30, 50))
        userCoupon = userCouponRepository.save(
            UserCouponEntity(null, user.id!!, coupon, UserCoupon.UserCouponStatus.AVAILABLE, LocalDateTime.now(), LocalDateTime.now().plusDays(30), null)
        )

        items = itemRepository.saveAll(
            listOf(
                ItemEntity(null, 1L, "상품 A", BigDecimal.valueOf(15000L),"http://test.png", Item.ItemStatus.SELLING, StockEntity(null, 50L)),
                ItemEntity(null, 1L, "상품 B", BigDecimal.valueOf(20000L),"http://test.png", Item.ItemStatus.SELLING, StockEntity(null, 50L)),
                ItemEntity(null, 1L, "상품 C", BigDecimal.valueOf(17000L),"http://test.png", Item.ItemStatus.SELLING, StockEntity(null, 50L)),
                ItemEntity(null, 1L, "상품 D", BigDecimal.valueOf(9000L),"http://test.png", Item.ItemStatus.SELLING, StockEntity(null, 50L)),
                ItemEntity(null, 1L, "상품 E", BigDecimal.valueOf(10000L),"http://test.png", Item.ItemStatus.SELLING, StockEntity(null, 50L))
            )
        )
    }

    fun getUser(): UserEntity {
        return this.user
    }

    fun getCoupon(): CouponEntity {
        return this.coupon
    }

    fun getUserCoupon(): UserCouponEntity {
        return this.userCoupon
    }

    fun getItems(): List<ItemEntity> {
        return this.items
    }

}