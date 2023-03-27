package com.nagp.bookingservice.dto;

import com.nagp.bookingservice.enums.BookingStatus;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class FlightBook {

    Integer id;
    private String flightNumber;
    private String origin;
    private String destination;
    private String flightDate;
    private Date bookingDate;
    private Float fare;
    private BookingStatus status;

    private Integer numberOfPassengers;
    //Set<Customer> customers;
    private Integer bookedBy;
}
