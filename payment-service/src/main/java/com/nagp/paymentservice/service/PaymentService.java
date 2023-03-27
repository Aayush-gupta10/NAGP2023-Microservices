package com.nagp.paymentservice.service;

import com.nagp.paymentservice.dto.Payment;

public interface PaymentService {

    Boolean paymentInitiated(Payment payment);

}
