package com.nagp.bookingservice.delegate;

import com.nagp.bookingservice.dto.BookingRecord;
import com.nagp.bookingservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingDelegate {
    @Autowired
    private BookingService bookingService;

    public List<BookingRecord> getBookingDetails() {
        return bookingService.getBookingDetails();
    }

    public String book(BookingRecord record) {
        return bookingService.book(record);
    }

    public BookingRecord getBooking(Integer id) {
        if(id == null)
            throw new RuntimeException("Booking ID cannot be null");
        return bookingService.getBooking(id);
    }
}
