package com.ecommerce.application.port.out

import com.ecommerce.domain.PointHistory

interface PointHistoryPort {

    fun saveRechargeHistory(pointHistory: PointHistory)

}