package com.ecommerce.application.port.out

import com.ecommerce.domain.item.Stock

interface StockPort {

    fun findStockById(id: Long): Stock

    fun deductStock(stock: Stock)

}