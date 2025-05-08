package com.ecommerce.adapter.out.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "CATEGORY")
class CategoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var name: String
): BaseEntity()