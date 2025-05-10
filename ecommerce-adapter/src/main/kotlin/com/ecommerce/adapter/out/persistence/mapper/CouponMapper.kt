package com.ecommerce.adapter.out.persistence.mapper

import com.ecommerce.adapter.out.persistence.entity.CouponEntity
import com.ecommerce.adapter.out.persistence.entity.UserCouponEntity
import com.ecommerce.domain.coupon.Coupon
import com.ecommerce.domain.coupon.UserCoupon
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface CouponMapper {

    fun toCoupon(entity: CouponEntity): Coupon

    fun toUserCoupon(entity: UserCouponEntity): UserCoupon

    @Mappings(
        Mapping(target = "createAt", ignore = true),
        Mapping(target = "modifiedAt", ignore = true)
    )
    fun toCouponEntity(domain: Coupon): CouponEntity

    @Mappings(
        Mapping(target = "createAt", ignore = true),
        Mapping(target = "modifiedAt", ignore = true)
    )
    fun toUserCouponEntity(domain: UserCoupon): UserCouponEntity

}