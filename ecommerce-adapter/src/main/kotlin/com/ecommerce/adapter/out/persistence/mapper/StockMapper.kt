package com.ecommerce.adapter.out.persistence.mapper

import com.ecommerce.adapter.out.persistence.entity.StockEntity
import com.ecommerce.domain.item.Stock
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface StockMapper {

    fun toStock(entity: StockEntity): Stock

    @Mappings(
        Mapping(target = "createAt", ignore = true),
        Mapping(target = "modifiedAt", ignore = true)
    )
    fun toStockEntity(domain: Stock): StockEntity

}