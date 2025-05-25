package com.ecommerce.adapter.out.persistence.entity

import com.ecommerce.domain.coupon.UserCoupon
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "USER_COUPON",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["user_id", "coupon_id"])
    ]
)
class UserCouponEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_coupon_id")
    val id: Long?,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    val coupon: CouponEntity,

    @Enumerated(EnumType.STRING)
    val status: UserCoupon.UserCouponStatus,

    val issuedAt: LocalDateTime,

    val expireAt: LocalDateTime,

    val usedAt: LocalDateTime?
): BaseEntity()