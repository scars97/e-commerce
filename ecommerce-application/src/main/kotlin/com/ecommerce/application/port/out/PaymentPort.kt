package com.ecommerce.application.port.out

import com.ecommerce.domain.payment.Payment

interface PaymentPort {

    fun savePayment(payment: Payment): Payment

}