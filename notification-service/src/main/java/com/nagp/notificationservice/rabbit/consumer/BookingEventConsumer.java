package com.nagp.notificationservice.rabbit.consumer;

import java.lang.invoke.MethodHandles;

import com.nagp.notificationservice.dto.Customer;
import com.nagp.notificationservice.dto.FlightBook;
import com.nagp.notificationservice.dto.HotelBook;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class BookingEventConsumer {

	@Autowired
	private EurekaClient eurekaClient;

	@Autowired
	private RestTemplate restTemplate;

	private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());


	@RabbitListener(queues = "${flight.queue.name}")
	public void recieveFlightBooking(String message) {
		try {
			logger.info("Recieved Flight Booking notification from Booking service");
			FlightBook bookingDetail = new ObjectMapper().readValue(message, FlightBook.class);
			logger.info("booking has been done for booking Id :{}",bookingDetail.getId());
			logger.info("Notification send to consumer");
			logger.info("booking details{}",bookingDetail);
			logger.info("Notification send to flight service provider");

			String userDetailUrl = "/user-service/user-detail?userId="+bookingDetail.getBookedBy();
			InstanceInfo userServiceInstance = eurekaClient.getNextServerFromEureka("user-service", false);

			ResponseEntity<Customer> responseEntity = restTemplate.exchange(userServiceInstance.getHomePageUrl() + userDetailUrl,
					HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), new ParameterizedTypeReference<Customer>() {
					});
			responseEntity.getBody();
			logger.info("customer Details {}",responseEntity.getBody());
			logger.info("Customer Name {}",responseEntity.getBody().getName());
			logger.info("Customer Address {}",responseEntity.getBody().getAddress());
			logger.info("Customer Contact Number {}",responseEntity.getBody().getContactNo());
			logger.info("Customer Email {}",responseEntity.getBody().getEmail());

		} catch (JsonParseException e) {
			logger.warn("Bad JSON in message: " + message);
		} catch (JsonMappingException e) {
			logger.warn("cannot map JSON to AdministrantRequest: " + message);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@RabbitListener(queues = "${hotel.queue.name}")
	public void recieveHotelBooking(String message) {
		try {
			logger.info("Recieved Hotel Booking notification from Booking service");
			HotelBook bookingDetail = new ObjectMapper().readValue(message, HotelBook.class);
			logger.info("booking has been done for booking Id :{}",bookingDetail.getId());
			logger.info("Notification send to consumer");
			logger.info("booking details{}",bookingDetail);
			logger.info("Notification send to hotel service provider");
			String userDetailUrl = "/user-service/user-detail?userId="+bookingDetail.getBookedBy();
			InstanceInfo userServiceInstance = eurekaClient.getNextServerFromEureka("user-service", false);

			ResponseEntity<Customer> responseEntity = restTemplate.exchange(userServiceInstance.getHomePageUrl() + userDetailUrl,
					HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), new ParameterizedTypeReference<Customer>() {
					});
			responseEntity.getBody();
			logger.info("customer Details {}",responseEntity.getBody());
			logger.info("Customer Name {}",responseEntity.getBody().getName());
			logger.info("Customer Address {}",responseEntity.getBody().getAddress());
			logger.info("Customer Contact Number {}",responseEntity.getBody().getContactNo());
			logger.info("Customer Email {}",responseEntity.getBody().getEmail());

		} catch (JsonParseException e) {
			logger.warn("Bad JSON in message: " + message);
		} catch (JsonMappingException e) {
			logger.warn("cannot map JSON to AdministrantRequest: " + message);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
