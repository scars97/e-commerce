package com.ecommerce.domain.item

import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import java.math.BigDecimal

class Item(
    val id: Long,
    val categoryId: Long,
    val name: String,
    val price: BigDecimal,
    val thumbnail: String,
    var status: ItemStatus,
    val stock: Stock
) {

    enum class ItemStatus {
        SELLING, SOLD_OUT
    }

    fun isSelling() {
        if (this.status != ItemStatus.SELLING) {
            throw CustomException(ErrorCode.ITEM_IS_NOT_ON_SALE)
        }
    }

    fun deductStock(count: Long) {
        if (this.stock.deduct(count) == 0L) {
            this.soldOut()
        }
    }

    fun soldOut() {
        this.status = ItemStatus.SOLD_OUT
    }

}