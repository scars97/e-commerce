package com.ecommerce.adapter.out.persistence.mapper

import com.ecommerce.adapter.out.persistence.entity.PaymentEntity
import com.ecommerce.domain.Payment

class PaymentMapper {

    companion object {
        fun toPaymentEntity(domain: Payment): PaymentEntity {
            return PaymentEntity(
                orderId = domain.orderId,
                userId = domain.userId,
                price = domain.price,
                status = domain.status
            )
        }

        fun toPayment(entity: PaymentEntity): Payment {
            return Payment(
                id = entity.id,
                orderId = entity.orderId,
                userId = entity.userId,
                price = entity.price,
                status = entity.status
            )
        }
    }

}
