package com.ecommerce.domain.user

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
        fun create(userId: Long, price: BigDecimal, status: PointHistoryStatus): PointHistory {
            return PointHistory(
                null,
                userId,
                status,
                price
            )
        }
    }

}