package com.ecommerce.adapter.fixture

import com.ecommerce.adapter.out.persistence.entity.UserEntity
import com.ecommerce.adapter.out.persistence.repository.UserJpaRepository
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class UserFixture(
    private val userRepository: UserJpaRepository
) {

    private lateinit var user: UserEntity
    private lateinit var users: List<UserEntity>

    fun createSingleUser(point: BigDecimal = BigDecimal.ZERO): UserEntity {
        user = userRepository.save(UserEntity(null, "user1", point))
        return user
    }

    fun createBulkUsers(totalUser: Int, point: BigDecimal = BigDecimal.ZERO): List<UserEntity> {
        users = (1 .. totalUser).map { i ->
            userRepository.save(
                UserEntity(null, "user$i", point)
            )
        }
        return users
    }

    fun getUser(): UserEntity {
        return this.user
    }

}