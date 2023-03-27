package com.nagp.paymentservice.dto;

import lombok.Data;

@Data
public class Payment {

    Integer paymentId;

    Integer bookingId;

    Float paymentCost;
    PaymentStatus paymentStatus;


}
