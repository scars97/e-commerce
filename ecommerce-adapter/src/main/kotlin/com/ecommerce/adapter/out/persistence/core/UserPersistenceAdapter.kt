package com.ecommerce.adapter.out.persistence.core

import com.ecommerce.adapter.out.persistence.mapper.UserMapper
import com.ecommerce.adapter.out.persistence.repository.UserJpaRepository
import com.ecommerce.application.port.out.UserPort
import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import com.ecommerce.domain.User
import org.springframework.stereotype.Repository

@Repository
class UserPersistenceAdapter(
    private val jpaRepository: UserJpaRepository
): UserPort {

    override fun findUserById(userId: Long): User {
        val user = jpaRepository.findById(userId).orElseThrow { CustomException(ErrorCode.USER_NOT_FOUND) }

        return UserMapper.toDomain(user)
    }

    override fun updateUser(user: User) {
        jpaRepository.save(
            UserMapper.toEntity(user)
        )
    }

}