package com.ecommerce.adapter.out.persistence.entity

import com.ecommerce.domain.coupon.Coupon
import jakarta.persistence.*

@Entity
@Table(name = "COUPON")
class CouponEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    val id: Long,

    val title: String,

    @Enumerated(EnumType.STRING)
    val type: Coupon.CouponType,

    val discount: Long,

    val expirationDay: Int
): BaseEntity() {
}