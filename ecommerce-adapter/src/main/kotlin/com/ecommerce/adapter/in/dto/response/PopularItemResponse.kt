package com.ecommerce.adapter.`in`.dto.response

import com.ecommerce.domain.item.PopularItem

data class PopularItemResponse(
    val rank: Int,
    val cumulativeSales: Long,
    val item: ItemResponse
) {

    companion object {
        fun of(popularItem: PopularItem): PopularItemResponse {
            return PopularItemResponse(
                rank = popularItem.rank,
                cumulativeSales = popularItem.cumulativeSales,
                item = ItemResponse.of(popularItem.item)
            )
        }
    }

}