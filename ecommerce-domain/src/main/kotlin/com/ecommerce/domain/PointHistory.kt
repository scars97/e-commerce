package com.ecommerce.domain

import java.math.BigDecimal
import java.time.LocalDateTime

class PointHistory(
    val id: Long?,
    val userId: Long,
    val status: PointHistoryStatus,
    val amount: BigDecimal,
    val createAt: LocalDateTime = LocalDateTime.now(),
    val modifiedAt: LocalDateTime = LocalDateTime.now()
) {

    enum class PointHistoryStatus {
        RECHARGE, USED
    }

    companion object {
        fun createAtRecharge(userId: Long, price: BigDecimal): PointHistory {
            return PointHistory(
                null,
                userId,
                PointHistoryStatus.RECHARGE,
                price
            )
        }

        fun createAtUsed(userId: Long, price: BigDecimal): PointHistory {
            return PointHistory(
                null,
                userId,
                PointHistoryStatus.USED,
                price
            )
        }
    }

}