package com.nagp.bookingservice.rabbit.listener;

import com.nagp.bookingservice.enums.BookingStatus;
import com.nagp.bookingservice.service.BookingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

public class BookingListener {

    @Autowired
    private BookingService bookingService;

    @RabbitListener(queues = "CheckINQ")
    public void processMessage(Integer bookingID ) {
        System.out.println(bookingID);
        bookingService.updateStatus(BookingStatus.CHECKED_IN, bookingID);
    }
}
