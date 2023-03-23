package com.nagp.masterdataservice.dto;

import com.nagp.masterdataservice.enums.FlightClass;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Flight {
    private String flightNum;
    private String departureLocation;
    private String arrivalLocation;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private FlightClass flightClass;
    private LocalDate flightDate;
}
