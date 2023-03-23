package com.nagp.notificationservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class HotelBook {
    Integer id;
    private String hotelName;
    private String city;
    private Integer numberOfRooms;
    private Integer price;
    private String status;
    private Date bookingDate;

    private Integer noOfGuests;

    //Set<Customer> customers;
    private Integer bookedBy;
}
