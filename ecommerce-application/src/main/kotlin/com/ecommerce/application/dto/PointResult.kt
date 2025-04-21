package com.ecommerce.application.dto

import com.ecommerce.domain.User
import java.math.BigDecimal

data class PointResult(
    val userId: Long,
    val remainingPoint: BigDecimal
) {

    companion object {
        fun of(user: User): PointResult {
            return PointResult(
                user.id,
                user.point
            )
        }
    }

}
