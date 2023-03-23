package com.nagp.notificationservice.dto;


import lombok.Data;

import java.util.Date;

@Data
public class FlightBook {

    Integer id;
    private String flightNumber;
    private String origin;
    private String destination;
    private String flightDate;
    private Date bookingDate;
    private String fare;
    private String status;

    private Integer numberOfPassengers;
    //Set<Customer> customers;
    private Integer bookedBy;
}
