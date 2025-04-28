package com.ecommerce.application.port.`in`

import com.ecommerce.application.dto.PaymentCommand
import com.ecommerce.domain.Payment

interface PaymentUseCase {

    fun pay(paymentCommand: PaymentCommand): Payment

}