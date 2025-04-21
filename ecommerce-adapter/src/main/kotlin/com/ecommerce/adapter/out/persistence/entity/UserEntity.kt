package com.ecommerce.adapter.out.persistence.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "USER")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long,

    val username: String,

    var point: BigDecimal,

    @CreatedDate
    val createAt: LocalDateTime,

    @LastModifiedDate
    val modifiedAt: LocalDateTime
)