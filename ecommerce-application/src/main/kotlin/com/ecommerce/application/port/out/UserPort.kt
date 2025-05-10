package com.ecommerce.application.port.out

import com.ecommerce.domain.user.User

interface UserPort {

    fun findUserById(userId: Long): User

    fun commandUser(user: User): User

}