package com.ecommerce.application.port.out

import com.ecommerce.domain.item.Stock

interface StockPort {

    fun findStockByItemId(itemId: Long): Stock

    fun deductStock(stock: Stock)

}