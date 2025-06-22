package com.ecommerce.adapter.`in`.dto.response

import com.ecommerce.domain.item.PopularItem

data class PopularItemResponse(
    val rank: Int,
    val item: ItemResponse
) {

    companion object {
        fun of(popularItem: PopularItem): PopularItemResponse {
            return PopularItemResponse(
                rank = popularItem.rank,
                item = ItemResponse.of(popularItem.item)
            )
        }
    }

}