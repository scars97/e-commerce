package com.ecommerce.adapter.out.persistence.mapper

import com.ecommerce.adapter.out.persistence.entity.CouponEntity
import com.ecommerce.adapter.out.persistence.entity.UserCouponEntity
import com.ecommerce.domain.coupon.Coupon
import com.ecommerce.domain.coupon.UserCoupon

class CouponMapper {

    companion object {
        fun toCoupon(entity: CouponEntity): Coupon {
            return Coupon(
                id = entity.id,
                title = entity.title,
                type = entity.type,
                discount = entity.discount,
                expirationDay = entity.expirationDay,
                quantity = entity.quantity
            )
        }

        fun toCouponEntity(domain: Coupon): CouponEntity {
            return CouponEntity(
                id = domain.id,
                title = domain.title,
                type = domain.type,
                discount = domain.discount,
                expirationDay = domain.expirationDay,
                quantity = domain.quantity
            )
        }

        fun toUserCoupon(entity: UserCouponEntity): UserCoupon {
            return UserCoupon(
                id = entity.id!!,
                userId = entity.userId,
                coupon = toCoupon(entity.coupon),
                status = entity.status,
                issuedAt = entity.issuedAt,
                expireAt = entity.expireAt,
                usedAt = entity.usedAt
            )
        }

        fun toUserCouponEntity(domain: UserCoupon): UserCouponEntity {
            return UserCouponEntity(
                id = domain.id,
                userId = domain.userId,
                coupon = toCouponEntity(domain.coupon),
                status = domain.status,
                issuedAt = domain.issuedAt,
                expireAt = domain.expireAt,
                usedAt = domain.usedAt
            )
        }
    }

}