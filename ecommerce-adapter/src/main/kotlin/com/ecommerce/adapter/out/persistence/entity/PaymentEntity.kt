package com.ecommerce.adapter.out.persistence.entity

import com.ecommerce.domain.Payment
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "PAYMENT")
class PaymentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="payment_id")
    val id: Long?,

    val orderId: Long,

    val userId: Long,

    val price: BigDecimal,

    @Enumerated(EnumType.STRING)
    val status: Payment.PaymentStatus
): BaseEntity()