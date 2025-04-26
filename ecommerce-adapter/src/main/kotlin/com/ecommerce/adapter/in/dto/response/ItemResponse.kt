package com.ecommerce.adapter.`in`.dto.response

import com.ecommerce.domain.item.Item
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
        fun of(item: Item): ItemResponse {
            return ItemResponse(
                itemId = item.id,
                categoryId = item.categoryId,
                name = item.name,
                price =item.price,
                thumbnail = item.thumbnail,
                status = item.status.name
            )
        }
    }

}
