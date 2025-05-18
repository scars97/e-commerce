package com.ecommerce.adapter.out.persistence.core

import com.ecommerce.adapter.out.persistence.mapper.StockMapper
import com.ecommerce.adapter.out.persistence.repository.StockJpaRepository
import com.ecommerce.application.port.out.StockPort
import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import com.ecommerce.domain.item.Stock
import org.springframework.stereotype.Repository

@Repository
class StockPersistenceAdapter(
    private val stockMapper: StockMapper,
    private val jpaRepository: StockJpaRepository
): StockPort {

    override fun findStockByItemId(itemId: Long): Stock {
        val stock = jpaRepository.findByItemIdWithLock(itemId)
            .orElseThrow { CustomException(ErrorCode.ITEM_NOT_FOUND) }

        return stockMapper.toStock(stock)
    }

    override fun deductStock(stock: Stock) {
        val entity = stockMapper.toStockEntity(stock)

        jpaRepository.save(entity)
    }
}
