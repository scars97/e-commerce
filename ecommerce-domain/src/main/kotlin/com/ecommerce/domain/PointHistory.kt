package com.ecommerce.domain

import java.math.BigDecimal
import java.time.LocalDateTime

class PointHistory(
    val id: Long?,
    val userId: Long,
    val status: PointHistoryStatus,
    val amount: BigDecimal,
    val createAt: LocalDateTime,
    val modifiedAt: LocalDateTime
) {

    enum class PointHistoryStatus {
        RECHARGE, USED
    }

    constructor(userId: Long, status: PointHistoryStatus, amount: BigDecimal, createAt: LocalDateTime, modifiedAt: LocalDateTime)
            :this(0, userId, status, amount, createAt, modifiedAt)

    companion object {
        fun createAtRecharge(userId: Long, price: BigDecimal): PointHistory {
            return PointHistory(
                userId,
                PointHistoryStatus.RECHARGE,
                price,
                LocalDateTime.now(),
                LocalDateTime.now()
            )
        }

        fun createAtUsed(userId: Long, price: BigDecimal): PointHistory {
            return PointHistory(
                userId,
                PointHistoryStatus.USED,
                price,
                LocalDateTime.now(),
                LocalDateTime.now()
            )
        }
    }

}