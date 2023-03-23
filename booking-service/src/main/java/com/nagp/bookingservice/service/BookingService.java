package com.nagp.bookingservice.service;

import com.nagp.bookingservice.dto.BookingRecord;
import com.nagp.bookingservice.enums.BookingStatus;

import java.util.List;

public interface BookingService {
    List<BookingRecord> getBookingDetails();

    String book(BookingRecord record);

    BookingRecord getBooking(Integer id);

    void updateStatus(BookingStatus checkedIn, Integer bookingID);
}
