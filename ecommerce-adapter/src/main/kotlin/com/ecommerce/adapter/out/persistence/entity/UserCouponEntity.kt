package com.ecommerce.adapter.out.persistence.entity

import com.ecommerce.domain.coupon.UserCoupon
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "USER_COUPON")
class UserCouponEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_coupon_id")
    val id: Long?,

    val userId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    val coupon: CouponEntity,

    @Enumerated(EnumType.STRING)
    val status: UserCoupon.UserCouponStatus,

    val expireAt: LocalDateTime,

    val usedAt: LocalDateTime?
): BaseEntity()