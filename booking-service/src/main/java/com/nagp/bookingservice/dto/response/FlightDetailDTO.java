package com.nagp.bookingservice.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FlightDetailDTO {
    private String flightNum;
    private String departureLocation;
    private String arrivalLocation;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String flightClass;
    private LocalDate flightDate;
    private Integer seatAvailable;
}
