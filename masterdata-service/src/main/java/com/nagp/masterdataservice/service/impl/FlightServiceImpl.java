package com.nagp.masterdataservice.service.impl;

import com.nagp.masterdataservice.dto.Flight;
import com.nagp.masterdataservice.enums.FlightClass;
import com.nagp.masterdataservice.service.FlightService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {
    @Override
    public List<Flight> getFlightDetails() {
        Flight flight = new Flight();
        flight.setFlightNum("436AWSTR");
        flight.setFlightDate(LocalDate.now().plusDays(2));
        flight.setFlightClass(FlightClass.Both);
        flight.setArrivalLocation("DEL");
        flight.setDepartureLocation("BEK");
        flight.setArrivalTime(LocalDateTime.now().plusDays(2).plusHours(4));
        flight.setDepartureTime(LocalDateTime.now().plusDays(2).plusHours(2));
        return List.of(flight);
    }
}
