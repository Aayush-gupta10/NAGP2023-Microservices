package com.nagp.bookingservice.controller;

import com.nagp.bookingservice.delegate.BookingDelegate;
import com.nagp.bookingservice.dto.BookingRecord;
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

    @GetMapping("/booking/all")
    public ResponseEntity<List<BookingRecord>> getBookingDetails() {
        log.info("BookingController.getBookingDetails started");
        List<BookingRecord> response = bookingDelegate.getBookingDetails();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/create-booking")
    public ResponseEntity<String> book(@RequestBody BookingRecord record){
        System.out.println("Booking Request" + record);
        return new ResponseEntity(bookingDelegate.book(record), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BookingRecord> getBooking(@PathVariable Integer id){
        BookingRecord response = bookingDelegate.getBooking(id);
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
