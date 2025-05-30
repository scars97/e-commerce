package com.ecommerce.adapter.out.persistence.core

import com.ecommerce.adapter.out.persistence.mapper.PaymentMapper
import com.ecommerce.adapter.out.persistence.repository.PaymentJpaRepository
import com.ecommerce.application.port.out.PaymentPort
import com.ecommerce.domain.payment.Payment
import org.springframework.stereotype.Repository

@Repository
class PaymentPersistenceAdapter(
    private val paymentMapper: PaymentMapper,
    private val jpaRepository: PaymentJpaRepository
): PaymentPort {

    override fun savePayment(payment: Payment): Payment {
        val savePayment = jpaRepository.save(
            paymentMapper.toPaymentEntity(payment)
        )

        return paymentMapper.toPayment(savePayment)
    }

}