package com.ecommerce.adapter.fixture

import com.ecommerce.adapter.out.persistence.entity.UserEntity
import com.ecommerce.adapter.out.persistence.repository.UserJpaRepository
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class UserFixture(
    private val userRepository: UserJpaRepository
) {

    fun createSingleUser(point: BigDecimal = BigDecimal.ZERO) {
        userRepository.save(UserEntity(null, "user1", BigDecimal.ZERO))
    }

    fun createBulkUsers(totalUser: Int, point: BigDecimal = BigDecimal.ZERO) {
        for (i in 1 .. totalUser) {
            userRepository.save(
                UserEntity(null, "user$i", point)
            )
        }
    }

}