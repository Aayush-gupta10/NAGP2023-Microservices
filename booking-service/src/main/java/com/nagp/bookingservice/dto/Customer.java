package com.nagp.bookingservice.dto;

import lombok.Data;

@Data
public class Customer {
    long id;
    String firstName;
    String lastName;
    String gender;
    private BookingRecord bookingRecord;
}
