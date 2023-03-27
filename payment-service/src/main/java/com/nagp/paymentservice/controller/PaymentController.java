package com.nagp.paymentservice.controller;

import com.nagp.paymentservice.dto.Payment;
import com.nagp.paymentservice.service.PaymentService;
import com.nagp.paymentservice.service.impl.PaymentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PaymentController
{

    @Autowired
    private PaymentService paymentService;
    @PostMapping("/payment")
    public ResponseEntity<Boolean> payment(@RequestBody Payment payment) {
        System.out.println("Payment Started");
        Boolean response = paymentService.paymentInitiated(payment);
//        if(userClientService.validateUser(userid)){
//            response = bookingDelegate.bookFlight(record,userid);
//        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
