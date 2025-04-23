package com.ecommerce.adapter.`in`.dto.response

import com.ecommerce.application.dto.ItemResult
import java.math.BigDecimal

data class ItemResponse(
    val itemId: Long,
    val categoryId: Long,
    val name: String,
    val price: BigDecimal,
    val thumbnail: String,
    val status: String
) {

    companion object {
        fun of(result: ItemResult): ItemResponse {
            return ItemResponse(
                itemId = result.itemId,
                categoryId = result.categoryId,
                name = result.name,
                price =result.price,
                thumbnail = result.thumbnail,
                status = result.status
            )
        }
    }

}
