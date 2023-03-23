package com.nagp.bookingservice.service.impl;

import com.nagp.bookingservice.dto.BookingRecord;
import com.nagp.bookingservice.dto.Customer;
import com.nagp.bookingservice.dto.Fare;
import com.nagp.bookingservice.dto.Flight;
import com.nagp.bookingservice.dto.response.FlightDetailDTO;
import com.nagp.bookingservice.dto.response.HotelDetailDTO;
import com.nagp.bookingservice.enums.BookingStatus;
import com.nagp.bookingservice.rabbit.sender.BookingSender;
import com.nagp.bookingservice.service.BookingService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BookingSender bookingSender;

    private final static List<BookingRecord> bookingRecords = new ArrayList<>();

    @Override
    public List<BookingRecord> getBookingDetails() {
        return bookingRecords;
    }

    @Override
    public String book(BookingRecord record) {
        log.info("calling master-data to get flight");
        //check inventory
        FlightDetailDTO inventory = findByFlightNumberAndFlightDate(record.getFlightNumber(), record.getFlightDate());
        if (inventory.getSeatAvailable() < record.getCustomers().size())
            throw new RuntimeException("No more seats available");

        log.info("successfully validated inventory" + inventory);
        log.info("calling master-data to update inventory");
        //update inventory
        inventory.setSeatAvailable(inventory.getSeatAvailable() - record.getCustomers().size());

        log.info("successfully updated inventory");
        //save booking
        record.setStatus(BookingStatus.BOOKING_CONFIRMED);
        record.setBookingDate(new Date());
        Set<Customer> Customers = record.getCustomers();
        Customers.forEach(Customer -> Customer.setBookingRecord(record));
        bookingRecords.add(record);
        log.info("Successfully saved booking");

        //send a message to search to update inventory
        log.info("sending a booking event");
        Map<String, Object> bookingDetails = new HashMap<String, Object>();
        bookingDetails.put("FLIGHT_NUMBER", record.getFlightNumber());
        bookingDetails.put("FLIGHT_DATE", record.getFlightDate());
        bookingDetails.put("NEW_INVENTORY", inventory.getSeatAvailable());
        bookingSender.send(bookingDetails);
        log.info("booking event successfully delivered " + bookingDetails);
        return "booking successful";
    }

    @HystrixCommand(fallbackMethod = "getFallbackForMasterdata")
    private HotelDetailDTO getHotelDetail() {
        String masterDataHotelUrl = "/master-data-service/hotel/all";
        InstanceInfo masterDataInstance = eurekaClient.getNextServerFromEureka("master-data-service", false);
        ResponseEntity<List<HotelDetailDTO>> responseEntity = restTemplate.exchange(masterDataInstance.getHomePageUrl() + masterDataHotelUrl,
                HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), new ParameterizedTypeReference<List<HotelDetailDTO>>() {
                });
        return responseEntity.getBody();
    }

    @HystrixCommand(fallbackMethod = "getFallbackForMasterdata")
    private FlightDetailDTO findByFlightNumberAndFlightDate(String flightNumber, String flightDate) {
        String masterDataFlightUrl = "/master-data-service/flight?flightNumber=" + flightNumber + "&flightDate=" + flightDate;
        InstanceInfo masterDataInstance = eurekaClient.getNextServerFromEureka("master-data-service", false);
        ResponseEntity<FlightDetailDTO> responseEntity = restTemplate.exchange(masterDataInstance.getHomePageUrl() + masterDataFlightUrl,
                HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), new ParameterizedTypeReference<FlightDetailDTO>() {
                });
        return responseEntity.getBody();
    }

    @Override
    public BookingRecord getBooking(Integer id) {
        return bookingRecords.get(id);
    }

    @Override
    public void updateStatus(BookingStatus status, Integer bookingId) {
        BookingRecord record = bookingRecords.get(bookingId);
        record.setStatus(status);
    }
}
