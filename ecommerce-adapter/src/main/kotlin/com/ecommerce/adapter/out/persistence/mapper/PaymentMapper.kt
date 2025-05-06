package com.ecommerce.adapter.out.persistence.mapper

import com.ecommerce.adapter.out.persistence.entity.PaymentEntity
import com.ecommerce.domain.Payment
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface PaymentMapper {

    fun toPayment(entity: PaymentEntity): Payment

    @Mappings(
        Mapping(target = "createAt", ignore = true),
        Mapping(target = "modifiedAt", ignore = true)
    )
    fun toPaymentEntity(domain: Payment): PaymentEntity

}
