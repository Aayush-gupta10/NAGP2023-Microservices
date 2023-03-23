package com.nagp.notificationservice.rabbit.consumer;

import java.lang.invoke.MethodHandles;

import com.nagp.notificationservice.dto.FlightBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BookingEventConsumer {
	
	private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

	
	@RabbitListener(queues = "${flight.queue.name}")
	public void recieveFlightBooking(String message) {
		try {
			logger.info("Recieved Flight Booking notification from Booking service");
			FlightBook bookingDetail = new ObjectMapper().readValue(message, FlightBook.class);
			logger.info("booking has been done for booking Id :{}",bookingDetail.getId());
		} catch (JsonParseException e) {
			logger.warn("Bad JSON in message: " + message);
		} catch (JsonMappingException e) {
			logger.warn("cannot map JSON to AdministrantRequest: " + message);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
