package com.ecommerce.application.port.out

import com.ecommerce.domain.user.PointHistory

interface PointHistoryPort {

    fun saveHistory(pointHistory: PointHistory)

}