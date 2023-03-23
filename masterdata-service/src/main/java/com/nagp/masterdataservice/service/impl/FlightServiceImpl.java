package com.nagp.masterdataservice.service.impl;

import com.nagp.masterdataservice.dto.Flight;
import com.nagp.masterdataservice.enums.FlightClass;
import com.nagp.masterdataservice.service.FlightService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private final static List<Flight> flights = new ArrayList<>();

    @PostConstruct
    public void createFlight(){
        Flight flight = new Flight();
        flight.setFlightNum("436AWSTR");
        flight.setFlightDate(LocalDate.now().plusDays(2));
        flight.setFlightClass(FlightClass.Both);
        flight.setArrivalLocation("DEL");
        flight.setDepartureLocation("BEK");
        flight.setArrivalTime(LocalDateTime.now().plusDays(2).plusHours(4));
        flight.setDepartureTime(LocalDateTime.now().plusDays(2).plusHours(2));
        flights.add(flight);
    }

    @Override
    public List<Flight> getFlightDetails() {
        return flights;
    }

    @Override
    public Flight findByFlightNumberAndFlightDate(String flightNumber, LocalDate flightDate) {
        return flights.stream().filter(flight -> flightNumber.equals(flight.getFlightNum()) &&
                flightDate.equals(flight.getFlightDate())).findFirst().get();
    }

    @Override
    public String updateFlight(Flight flight) {
        flights.get(0).setSeatAvailable(flight.getSeatAvailable());
        return "Flight Updated";
    }
}
