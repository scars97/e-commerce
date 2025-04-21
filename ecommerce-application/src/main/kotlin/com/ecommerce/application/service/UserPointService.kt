package com.ecommerce.application.service

import com.ecommerce.application.dto.PointCommand
import com.ecommerce.application.dto.PointResult
import com.ecommerce.application.port.`in`.UserPointUseCase
import com.ecommerce.application.port.out.PointHistoryPort
import com.ecommerce.application.port.out.UserPort
import com.ecommerce.domain.PointHistory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserPointService(
    private val userPort: UserPort,
    private val pointHistoryPort: PointHistoryPort
): UserPointUseCase {

    @Transactional
    override fun pointRecharge(command: PointCommand): PointResult {
        val user = userPort.findUserById(command.userId)
        
        userPort.updateUser(user.pointRecharge(command.price))

        pointHistoryPort.saveRechargeHistory(
            PointHistory.createAtRecharge(command.userId, command.price)
        )
        
        return PointResult.of(user)
    }

    @Transactional(readOnly = true)
    override fun getPoint(userId: Long): PointResult {
        return PointResult.of(
            userPort.findUserById(userId)
        )
    }

}