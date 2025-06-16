package com.ecommerce.application.service

import com.ecommerce.application.dto.PointCommand
import com.ecommerce.application.port.`in`.UserPointUseCase
import com.ecommerce.application.port.out.UserPort
import com.ecommerce.domain.event.CreatePointHistory
import com.ecommerce.domain.user.PointHistory
import com.ecommerce.domain.user.User
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserPointService(
    private val userPort: UserPort,
    private val eventPublisher: ApplicationEventPublisher
): UserPointUseCase {

    @Transactional
    override fun pointRecharge(command: PointCommand): User {
        val user = userPort.findUserById(command.userId)
        
        userPort.commandUser(user.pointRecharge(command.price))

        eventPublisher.publishEvent(CreatePointHistory(command.userId, command.price, PointHistory.PointHistoryStatus.RECHARGE))
        
        return user
    }

    @Transactional(readOnly = true)
    override fun getPoint(userId: Long): User {
        return userPort.findUserById(userId)
    }

}