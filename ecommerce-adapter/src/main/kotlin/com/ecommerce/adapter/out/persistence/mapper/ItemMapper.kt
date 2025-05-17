package com.ecommerce.adapter.out.persistence.mapper

import com.ecommerce.adapter.out.persistence.entity.ItemEntity
import com.ecommerce.domain.item.Item
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface ItemMapper {

    fun toItem(entity: ItemEntity): Item

    @Mappings(
        Mapping(target = "createAt", ignore = true),
        Mapping(target = "modifiedAt", ignore = true)
    )
    fun toItemEntity(domain: Item): ItemEntity

}