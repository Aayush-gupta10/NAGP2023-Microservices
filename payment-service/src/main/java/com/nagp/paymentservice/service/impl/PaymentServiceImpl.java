package com.nagp.paymentservice.service.impl;

import com.nagp.paymentservice.dto.Payment;
import com.nagp.paymentservice.dto.PaymentStatus;
import com.nagp.paymentservice.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public Boolean paymentInitiated(Payment payment) {
        if(payment.getBookingId() > 10)
        {
            payment.setPaymentId(123);
            payment.setPaymentStatus(PaymentStatus.Declined);
            return false;
        }
        else{
            payment.setPaymentId(456);
            payment.setPaymentStatus(PaymentStatus.Successful);
            return true;
        }
    }
}
