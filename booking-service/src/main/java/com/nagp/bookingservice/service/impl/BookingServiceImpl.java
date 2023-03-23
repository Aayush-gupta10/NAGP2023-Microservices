package com.nagp.bookingservice.service.impl;

import com.nagp.bookingservice.dto.*;
import com.nagp.bookingservice.dto.response.FlightDetailDTO;
import com.nagp.bookingservice.dto.response.HotelDetailDTO;
import com.nagp.bookingservice.enums.BookingStatus;
import com.nagp.bookingservice.rabbit.sender.BookingEventProducer;
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
    BookingEventProducer bookingEventProducer;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BookingEventProducer bookingSender;

    private final static List<FlightBook> flightbookingRecords = new ArrayList<>();

    @Override
    public List<FlightBook> getFlightBookingDetails() {
        return flightbookingRecords;
    }

    @Override
    public String bookFlight(FlightBook record) {
        log.info("calling master-data to get flight");
        //check inventory
        FlightDetailDTO inventory = findByFlightNumberAndFlightDate(record.getFlightNumber(), record.getFlightDate());
        if (inventory.getSeatAvailable() < record.getNumberOfPassengers())
            throw new RuntimeException("No more seats available");

        log.info("successfully validated inventory" + inventory);
        log.info("calling master-data to update inventory");
        //update inventory
        inventory.setSeatAvailable(inventory.getSeatAvailable() - record.getNumberOfPassengers());
        updateFlight(inventory);
        log.info("successfully updated inventory");
        //save booking
        //need to write code for payment service if done then confirmed
        record.setStatus(BookingStatus.BOOKING_CONFIRMED);
        record.setBookingDate(new Date());
        //Set<Customer> Customers = record.getCustomers();
        //Customers.forEach(Customer -> Customer.setBookingRecord(record));
        flightbookingRecords.add(record);
        log.info("Successfully saved booking");

        //send a message to search to update inventory
        log.info("sending a booking event");
        try {
            sendFlightBookingNotification(record);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        Map<String, Object> bookingDetails = new HashMap<String, Object>();
//        bookingDetails.put("FLIGHT_NUMBER", record.getFlightNumber());
//        bookingDetails.put("FLIGHT_DATE", record.getFlightDate());
//        bookingDetails.put("NEW_INVENTORY", inventory.getSeatAvailable());
//        bookingSender.send(bookingDetails);
//        log.info("booking event successfully delivered " + bookingDetails);
        return "booking successful";
    }

    private final static List<HotelBook> hotelbookingRecords = new ArrayList<>();

    @Override
    public List<HotelBook> getHotelBookingDetails() {
        return hotelbookingRecords;
    }

    @Override
    public String bookHotel(HotelBook record) {
        log.info("calling master-data to get flight");
        //check inventory
        HotelDetailDTO inventory = findByHotelNameandCity(record.getHotelName(), record.getCity());
        if (inventory.getNumberOfRoomsAvailable() < record.getNumberOfRooms())
            throw new RuntimeException("No more rooms available");

        log.info("successfully validated inventory" + inventory);
        log.info("calling master-data to update inventory");
        //update inventory
        inventory.setNumberOfRoomsAvailable(inventory.getNumberOfRoomsAvailable() - record.getNumberOfRooms());
        updateHotel(inventory);
        log.info("successfully updated inventory");
        //save booking
        //need to write code for payment service if done then confirmed
        record.setStatus(BookingStatus.BOOKING_CONFIRMED);
        record.setBookingDate(new Date());
        //Set<Customer> Customers = record.getCustomers();
        //Customers.forEach(Customer -> Customer.setBookingRecord(record));
        hotelbookingRecords.add(record);
        log.info("Successfully saved booking");

        //send a message to search to update inventory
        log.info("sending a booking event");
        try {
            sendHotelBookingNotification(record);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        Map<String, Object> bookingDetails = new HashMap<String, Object>();
//        bookingDetails.put("FLIGHT_NUMBER", record.getFlightNumber());
//        bookingDetails.put("FLIGHT_DATE", record.getFlightDate());
//        bookingDetails.put("NEW_INVENTORY", inventory.getSeatAvailable());
//        bookingSender.send(bookingDetails);
//        log.info("booking event successfully delivered " + bookingDetails);
        return "booking successful";
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

    @HystrixCommand(fallbackMethod = "getFallbackForMasterdata")
    private HotelDetailDTO findByHotelNameandCity(String hotelName, String city) {
        String masterDataHotelUrl = "/master-data-service/hotel?hotelName=" + hotelName + "&city=" + city;
        InstanceInfo masterDataInstance = eurekaClient.getNextServerFromEureka("master-data-service", false);
        ResponseEntity<HotelDetailDTO> responseEntity = restTemplate.exchange(masterDataInstance.getHomePageUrl() + masterDataHotelUrl,
                HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), new ParameterizedTypeReference<HotelDetailDTO>() {
                });
        return responseEntity.getBody();
    }

    @HystrixCommand(fallbackMethod = "getFallbackForMasterdata")
    private String updateFlight(FlightDetailDTO flightDetails) {
        String masterDataFlightUrl = "/master-data-service/update-flight";
        InstanceInfo masterDataInstance = eurekaClient.getNextServerFromEureka("master-data-service", false);
        HttpEntity<FlightDetailDTO> entity = new HttpEntity<>(flightDetails);
        ResponseEntity<String> responseEntity = restTemplate.exchange(masterDataInstance.getHomePageUrl() + masterDataFlightUrl,
                HttpMethod.PUT, entity, new ParameterizedTypeReference<String>() {
                });
        return responseEntity.getBody();
    }

    private String updateHotel(HotelDetailDTO hotelDetails) {
        String masterDataHotelUrl = "/master-data-service/update-hotel";
        InstanceInfo masterDataInstance = eurekaClient.getNextServerFromEureka("master-data-service", false);
        HttpEntity<HotelDetailDTO> entity = new HttpEntity<>(hotelDetails);
        ResponseEntity<String> responseEntity = restTemplate.exchange(masterDataInstance.getHomePageUrl() + masterDataHotelUrl,
                HttpMethod.PUT, entity, new ParameterizedTypeReference<String>() {
                });
        return responseEntity.getBody();
    }

    private void sendHotelBookingNotification(HotelBook hotelDetails) throws Exception {
        bookingEventProducer.produceHotelBookingEvent(hotelDetails);

    }

    private void sendFlightBookingNotification(FlightBook flightDetails) throws Exception {
        bookingEventProducer.produceFLightBookingEvent(flightDetails);

    }

    @Override
    public FlightBook getFlightBooking(Integer id) {
        return flightbookingRecords.get(id);
    }

    @Override
    public HotelBook getHotelBooking(Integer id) {
        return hotelbookingRecords.get(id);
    }

//    @Override
//    public void updateStatus(BookingStatus status, Integer bookingId) {
//        BookingRecord record = bookingRecords.get(bookingId);
//        record.setStatus(status);
//    }
}
