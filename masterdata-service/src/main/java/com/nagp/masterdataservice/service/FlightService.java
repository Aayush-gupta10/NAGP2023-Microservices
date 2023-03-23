package com.nagp.masterdataservice.service;

import com.nagp.masterdataservice.dto.Flight;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    List<Flight> getFlightDetails();

    Flight findByFlightNumberAndFlightDate(String flightNumber, LocalDate flightDate);

    String updateFlight(Flight flight);
}
