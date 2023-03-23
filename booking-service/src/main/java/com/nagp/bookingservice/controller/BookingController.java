package com.nagp.bookingservice.controller;

import com.nagp.bookingservice.delegate.BookingDelegate;
import com.nagp.bookingservice.dto.BookingRecord;
import com.nagp.bookingservice.dto.FlightBook;
import com.nagp.bookingservice.dto.HotelBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class BookingController {

    @Autowired
    private BookingDelegate bookingDelegate;

//    @GetMapping("/booking/all")
//    public ResponseEntity<List<BookingRecord>> getBookingDetails() {
//        log.info("BookingController.getBookingDetails started");
//        List<BookingRecord> response = bookingDelegate.getBookingDetails();
//        return new ResponseEntity(response, HttpStatus.OK);
//    }

    @PostMapping("/create-flight-booking")
    public ResponseEntity<String> bookFlight(@RequestBody FlightBook record){
        System.out.println("Booking Request" + record);
        return new ResponseEntity(bookingDelegate.bookFlight(record), HttpStatus.OK);
    }

    @PostMapping("/create-hotel-booking")
    public ResponseEntity<String> bookHotel(@RequestBody HotelBook record){
        System.out.println("Booking Request" + record);
        return new ResponseEntity(bookingDelegate.bookHotel(record), HttpStatus.OK);
    }

    @GetMapping("/get/flight-booking/{id}")
    public ResponseEntity<FlightBook> getFlightBooking(@PathVariable Integer id){
        FlightBook response = bookingDelegate.getFlightBooking(id);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/get/hotel-booking/{id}")
    public ResponseEntity<HotelBook> getHotelBooking(@PathVariable Integer id){
        HotelBook response = bookingDelegate.getHotelBooking(id);
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
