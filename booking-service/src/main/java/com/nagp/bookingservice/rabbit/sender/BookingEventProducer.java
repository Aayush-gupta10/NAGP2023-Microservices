package com.nagp.bookingservice.rabbit.sender;

import java.lang.invoke.MethodHandles;

import com.nagp.bookingservice.dto.FlightBook;
import com.nagp.bookingservice.dto.HotelBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class BookingEventProducer {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    @Value("${flight.fanout.exchange}")
    private String flightfanoutExchange;

    @Value("${hotel.fanout.exchange}")
    private String hotelfanoutExchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void produceFLightBookingEvent(FlightBook bookingDetail) throws Exception {
        rabbitTemplate.setExchange(flightfanoutExchange);
        rabbitTemplate.convertAndSend(new ObjectMapper().writeValueAsString(bookingDetail));
    }

    public void produceHotelBookingEvent(HotelBook bookingDetail) throws Exception {
        rabbitTemplate.setExchange(hotelfanoutExchange);
        rabbitTemplate.convertAndSend(new ObjectMapper().writeValueAsString(bookingDetail));
    }
}
