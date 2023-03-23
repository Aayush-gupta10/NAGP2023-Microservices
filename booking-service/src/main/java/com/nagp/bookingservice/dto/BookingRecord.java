package com.nagp.bookingservice.dto;

import com.nagp.bookingservice.enums.BookingStatus;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class BookingRecord {
    Integer id;
    private String flightNumber;
    private String origin;
    private String destination;
    private String flightDate;
    private Date bookingDate;
    private String fare;
    private BookingStatus status;
    Set<Customer> customers;
}
