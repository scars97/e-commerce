package com.ecommerce.adapter.out.persistence.core

import com.ecommerce.adapter.out.persistence.mapper.PointHistoryMapper
import com.ecommerce.adapter.out.persistence.repository.PointHistoryJpaRepository
import com.ecommerce.application.port.out.PointHistoryPort
import com.ecommerce.domain.user.PointHistory
import org.springframework.stereotype.Repository

@Repository
class PointHistoryPersistenceAdapter(
    private val pointHistoryMapper: PointHistoryMapper,
    private val jpaRepository: PointHistoryJpaRepository
): PointHistoryPort {

    override fun saveHistory(pointHistory: PointHistory) {
        jpaRepository.save(
            pointHistoryMapper.toPointHistoryEntity(pointHistory)
        )
    }

}