package com.ecommerce.adapter.out.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "CATEGORY")
class CategoryEntity(
    @Id
    @GeneratedValue
    val id: Long,

    var name: String
): BaseEntity()