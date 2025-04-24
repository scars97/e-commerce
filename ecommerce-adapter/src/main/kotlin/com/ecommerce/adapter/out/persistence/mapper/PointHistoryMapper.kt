package com.ecommerce.adapter.out.persistence.mapper

import com.ecommerce.adapter.out.persistence.entity.PointHistoryEntity
import com.ecommerce.domain.PointHistory

class PointHistoryMapper {

    companion object {
        fun toEntity(domain: PointHistory): PointHistoryEntity {
            return PointHistoryEntity(
                userId = domain.userId,
                status = domain.status,
                amount = domain.amount
            )
        }
    }

}