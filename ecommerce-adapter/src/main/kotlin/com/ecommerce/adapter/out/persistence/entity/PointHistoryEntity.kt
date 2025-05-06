package com.ecommerce.adapter.out.persistence.entity

import com.ecommerce.domain.PointHistory
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "POINT_HISTORY")
class PointHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id")
    val id: Long?,

    val userId: Long,

    @Enumerated(EnumType.STRING)
    val status: PointHistory.PointHistoryStatus,

    val amount: BigDecimal
): BaseEntity()