package com.nagp.bookingservice.delegate;

import com.nagp.bookingservice.dto.BookingRecord;
import com.nagp.bookingservice.dto.FlightBook;
import com.nagp.bookingservice.dto.HotelBook;
import com.nagp.bookingservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingDelegate {
    @Autowired
    private BookingService bookingService;

//    public List<BookingRecord> getBookingDetails() {
//        return bookingService.getBookingDetails();
//    }

    public String bookFlight(FlightBook record,Integer userid) {
        return bookingService.bookFlight(record,userid);
    }

    public String bookHotel(HotelBook record,Integer userid) {
        return bookingService.bookHotel(record,userid);
    }

    public FlightBook getFlightBooking(Integer id) {
        if(id == null)
            throw new RuntimeException("Booking ID cannot be null");
        return bookingService.getFlightBooking(id);
    }

    public HotelBook getHotelBooking(Integer id) {
        if(id == null)
            throw new RuntimeException("Booking ID cannot be null");
        return bookingService.getHotelBooking(id);
    }
}
