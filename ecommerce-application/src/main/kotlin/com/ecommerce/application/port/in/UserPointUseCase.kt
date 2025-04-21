package com.ecommerce.application.port.`in`

import com.ecommerce.application.dto.PointCommand
import com.ecommerce.application.dto.PointResult

interface UserPointUseCase {

    fun pointRecharge(command: PointCommand): PointResult

    fun getPoint(userId: Long): PointResult

}