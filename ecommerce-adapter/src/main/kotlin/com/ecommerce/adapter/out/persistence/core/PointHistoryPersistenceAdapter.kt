package com.ecommerce.adapter.out.persistence.core

import com.ecommerce.adapter.out.persistence.mapper.PointHistoryMapper
import com.ecommerce.adapter.out.persistence.repository.PointHistoryJapRepository
import com.ecommerce.application.port.out.PointHistoryPort
import com.ecommerce.domain.PointHistory
import org.springframework.stereotype.Repository

@Repository
class PointHistoryPersistenceAdapter(
    private val pointHistoryMapper: PointHistoryMapper,
    private val jpaRepository: PointHistoryJapRepository
): PointHistoryPort {

    override fun saveHistory(pointHistory: PointHistory) {
        jpaRepository.save(
            pointHistoryMapper.toPointHistoryEntity(pointHistory)
        )
    }

}