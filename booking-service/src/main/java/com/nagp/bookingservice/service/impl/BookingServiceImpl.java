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
    public String bookFlight(FlightBook record, Integer userid) {
        log.info("calling master-data to get flight");
        //check inventory
        FlightDetailDTO inventory = findByFlightNumberAndFlightDate(record.getFlightNumber(), record.getFlightDate());
        if (inventory.getSeatAvailable() < record.getNumberOfPassengers())
            throw new RuntimeException("No more seats available");

        log.info("successfully validated inventory" + inventory);
        log.info("calling master-data to update inventory");

        inventory.setSeatAvailable(inventory.getSeatAvailable() - record.getNumberOfPassengers());
        log.info("inventory{}",inventory);
        updateFlight(inventory);
        log.info("successfully updated inventory");

        record.setStatus(BookingStatus.BOOKING_INITIATED);

        boolean payment = flightPaymentSatus(record);
        if(!payment)
        {
            record.setStatus(BookingStatus.BOOKING_REJECTED);
            inventory.setSeatAvailable(inventory.getSeatAvailable() + record.getNumberOfPassengers());
            log.info("inventory{}",inventory);
            updateFlight(inventory);
            log.info("Booking got canceled due to payment failure");
            record.setBookedBy(userid);
            log.info("sending a booking failure event");
            try {
                sendFlightBookingNotification(record);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return "Booking got canceled due to payment failure, refund initiated";
        }
        else{
            record.setStatus(BookingStatus.BOOKING_CONFIRMED);
            record.setBookingDate(new Date());
            record.setBookedBy(userid);

            flightbookingRecords.add(record);
            log.info("Successfully saved booking");

            //send a message to search to update inventory
            log.info("sending a booking event");
            try {
                sendFlightBookingNotification(record);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return "booking successful";
        }
    }

    private final static List<HotelBook> hotelbookingRecords = new ArrayList<>();

    @Override
    public List<HotelBook> getHotelBookingDetails() {
        return hotelbookingRecords;
    }

    @Override
    public String bookHotel(HotelBook record, Integer userid) {
        log.info("calling master-data to get hotel");

        HotelDetailDTO inventory = findByHotelNameandCity(record.getHotelName(), record.getCity());
        if (inventory.getNumberOfRoomsAvailable() < record.getNumberOfRooms())
            throw new RuntimeException("No more rooms available");

        log.info("successfully validated inventory" + inventory);
        log.info("calling master-data to update hotel inventory");

        inventory.setNumberOfRoomsAvailable(inventory.getNumberOfRoomsAvailable() - record.getNumberOfRooms());
        log.info("inventory{}",inventory);
        updateHotel(inventory);
        log.info("successfully updated hotel inventory");
        record.setStatus(BookingStatus.BOOKING_INITIATED);

        boolean payment = hotelPaymentSatus(record);
        if(!payment)
        {
            record.setStatus(BookingStatus.BOOKING_REJECTED);
            inventory.setNumberOfRoomsAvailable(inventory.getNumberOfRoomsAvailable() + record.getNumberOfRooms());
            log.info("inventory{}",inventory);
            updateHotel(inventory);
            log.info("successfully updated hotel inventory");
            log.info("Booking got canceled due to payment failure");
            record.setBookedBy(userid);
            log.info("sending a booking failure event");
            try {
                sendHotelBookingNotification(record);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return "Booking got canceled due to payment failure, refund initiated";
        }
        else{
            record.setStatus(BookingStatus.BOOKING_CONFIRMED);
            record.setBookingDate(new Date());
            record.setBookedBy(userid);

            hotelbookingRecords.add(record);
            log.info("Successfully saved booking");

            //send a message to search to update inventory
            log.info("sending a booking event");
            try {
                sendHotelBookingNotification(record);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return "booking successful";
        }
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

    private Boolean flightPaymentSatus(FlightBook flightDetails)
    {
        Payment payment = new Payment();
        payment.setBookingId(flightDetails.getId());
        payment.setPaymentCost(flightDetails.getFare());
        payment.setPaymentId(12);
        payment.setPaymentStatus(PaymentStatus.Initiated);
        HttpEntity<Payment> httpEntity =new HttpEntity<>(payment);
        String paymentUrl = "/payment-service/payment";
        InstanceInfo paymentInstance = eurekaClient.getNextServerFromEureka("payment-service", false);
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(paymentInstance.getHomePageUrl() + paymentUrl,
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<Boolean>() {});
        return responseEntity.getBody();
    }

    private Boolean hotelPaymentSatus(HotelBook hotelDetails)
    {
        Payment payment = new Payment();
        payment.setBookingId(hotelDetails.getId());
        payment.setPaymentCost(hotelDetails.getPrice());
        payment.setPaymentId(12);
        payment.setPaymentStatus(PaymentStatus.Initiated);
        HttpEntity<Payment> httpEntity =new HttpEntity<>(payment);
        String paymentUrl = "/payment-service/payment";
        InstanceInfo paymentInstance = eurekaClient.getNextServerFromEureka("payment-service", false);
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(paymentInstance.getHomePageUrl() + paymentUrl,
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<Boolean>() {});
        return responseEntity.getBody();
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
