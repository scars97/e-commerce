package com.ecommerce.application.port.`in`

import com.ecommerce.application.dto.PointCommand
import com.ecommerce.domain.User

interface UserPointUseCase {

    fun pointRecharge(command: PointCommand): User

    fun getPoint(userId: Long): User

}