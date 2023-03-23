package com.nagp.masterdataservice.delegate;

import com.nagp.masterdataservice.dto.Flight;
import com.nagp.masterdataservice.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class FlightDelegate {

    @Autowired
    private FlightService flightService;

    public List<Flight> getFlightDetails() {
        return flightService.getFlightDetails();
    }

    public Flight findByFlightNumberAndFlightDate(String flightNumber, LocalDate flightDate) {
        return flightService.findByFlightNumberAndFlightDate(flightNumber, flightDate);
    }

    public String updateFlight(Flight flight) {
        return flightService.updateFlight(flight);
    }
}
