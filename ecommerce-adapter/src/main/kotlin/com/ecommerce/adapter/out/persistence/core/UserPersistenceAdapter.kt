package com.ecommerce.adapter.out.persistence.core

import com.ecommerce.adapter.out.persistence.mapper.UserMapper
import com.ecommerce.adapter.out.persistence.repository.UserJpaRepository
import com.ecommerce.application.port.out.UserPort
import com.ecommerce.common.exception.CustomException
import com.ecommerce.common.exception.ErrorCode
import com.ecommerce.domain.user.User
import org.springframework.stereotype.Repository

@Repository
class UserPersistenceAdapter(
    private val userMapper: UserMapper,
    private val jpaRepository: UserJpaRepository
): UserPort {

    override fun findUserById(userId: Long): User {
        val user = jpaRepository.findById(userId).orElseThrow { CustomException(ErrorCode.USER_NOT_FOUND) }

        return userMapper.toUser(user)
    }

    override fun commandUser(user: User): User {
        val savedUser = jpaRepository.save(
            userMapper.toUserEntity(user)
        )

        return userMapper.toUser(savedUser)
    }

}