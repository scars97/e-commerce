package com.ecommerce.application.port.out

import com.ecommerce.domain.User

interface UserPort {

    fun findUserById(userId: Long): User

    fun updateUser(user: User)

}