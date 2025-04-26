package com.ecommerce.application.service

import com.ecommerce.application.dto.PointCommand
import com.ecommerce.application.port.`in`.UserPointUseCase
import com.ecommerce.application.port.out.PointHistoryPort
import com.ecommerce.application.port.out.UserPort
import com.ecommerce.domain.PointHistory
import com.ecommerce.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserPointService(
    private val userPort: UserPort,
    private val pointHistoryPort: PointHistoryPort
): UserPointUseCase {

    @Transactional
    override fun pointRecharge(command: PointCommand): User {
        val user = userPort.findUserById(command.userId)
        
        userPort.updateUser(user.pointRecharge(command.price))

        pointHistoryPort.saveRechargeHistory(
            PointHistory.createAtRecharge(command.userId, command.price)
        )
        
        return user
    }

    @Transactional(readOnly = true)
    override fun getPoint(userId: Long): User {
        return userPort.findUserById(userId)
    }

}