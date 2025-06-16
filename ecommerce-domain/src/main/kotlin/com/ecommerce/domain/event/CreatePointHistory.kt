package com.ecommerce.domain.event

import com.ecommerce.domain.user.PointHistory
import java.math.BigDecimal

data class CreatePointHistory(
    val userId: Long,
    val price: BigDecimal,
    val status: PointHistory.PointHistoryStatus
)