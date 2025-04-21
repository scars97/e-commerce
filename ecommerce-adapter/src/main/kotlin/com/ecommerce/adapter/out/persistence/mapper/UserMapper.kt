package com.ecommerce.adapter.out.persistence.mapper

import com.ecommerce.adapter.out.persistence.entity.UserEntity
import com.ecommerce.domain.User
import java.math.BigDecimal
import java.time.LocalDateTime

data class UserMapper(
    val id: Long,
    val username: String,
    val point: BigDecimal,
    val createAt: LocalDateTime,
    val modifiedAt: LocalDateTime
) {

    companion object {
        fun toDomain(entity: UserEntity): User {
            return User(
                id = entity.id ,
                username = entity.username,
                point = entity.point,
                createAt = entity.createAt,
                modifiedAt = entity.modifiedAt
            )
        }

        fun toEntity(domain: User): UserEntity {
            return UserEntity(
                id = domain.id,
                username = domain.username,
                point = domain.point,
                createAt = domain.createAt,
                modifiedAt = domain.modifiedAt
            )
        }
    }

}
