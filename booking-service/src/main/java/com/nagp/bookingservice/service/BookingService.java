package com.nagp.bookingservice.service;

import com.nagp.bookingservice.dto.BookingRecord;
import com.nagp.bookingservice.dto.FlightBook;
import com.nagp.bookingservice.dto.HotelBook;
import com.nagp.bookingservice.enums.BookingStatus;

import java.util.List;

public interface BookingService {
    List<FlightBook> getFlightBookingDetails();

    String bookFlight(FlightBook record, Integer userid);

    String bookHotel(HotelBook record, Integer userid);

    List<HotelBook> getHotelBookingDetails();
    FlightBook getFlightBooking(Integer id);
    HotelBook getHotelBooking(Integer id);

    //void updateStatus(BookingStatus checkedIn, Integer bookingID);
}
