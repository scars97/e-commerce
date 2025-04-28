package com.ecommerce.adapter.out.persistence.repository

import com.ecommerce.adapter.out.persistence.entity.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaRepository: JpaRepository<PaymentEntity, Long>