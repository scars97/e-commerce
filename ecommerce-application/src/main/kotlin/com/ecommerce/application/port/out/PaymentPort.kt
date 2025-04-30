package com.ecommerce.application.port.out

import com.ecommerce.domain.Payment

interface PaymentPort {

    fun savePayment(payment: Payment): Payment

}