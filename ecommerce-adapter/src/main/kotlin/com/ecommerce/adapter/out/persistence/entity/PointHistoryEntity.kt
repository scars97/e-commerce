package com.ecommerce.adapter.out.persistence.entity

import com.ecommerce.domain.PointHistory
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "POINT_HISTORY")
class PointHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id")
    val id: Long,

    val userId: Long,

    val status: PointHistory.PointHistoryStatus,

    val amount: BigDecimal,

    @CreatedDate
    val createAt: LocalDateTime,

    @LastModifiedDate
    val modifiedAt: LocalDateTime
) {

    constructor(
        userId: Long,
        status: PointHistory.PointHistoryStatus,
        amount: BigDecimal,
        createAt: LocalDateTime,
        modifiedAt: LocalDateTime
    ) :this(0, userId, status, amount, createAt, modifiedAt)

}