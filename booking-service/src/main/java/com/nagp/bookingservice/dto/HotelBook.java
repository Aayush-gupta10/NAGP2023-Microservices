package com.nagp.bookingservice.dto;

import com.nagp.bookingservice.enums.BookingStatus;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class HotelBook {
    Integer id;
    private String hotelName;
    private String city;
    private Integer numberOfRooms;
    private Integer price;
    private BookingStatus status;
    private Date bookingDate;

    private Integer noOfGuests;

    //Set<Customer> customers;
    private Integer bookedBy;
}
