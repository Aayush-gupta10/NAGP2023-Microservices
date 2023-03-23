package com.nagp.masterdataservice.controller;

import com.nagp.masterdataservice.delegate.FlightDelegate;
import com.nagp.masterdataservice.dto.Flight;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
public class FlightController {

    @Autowired
    private FlightDelegate flightDelegate;

    @GetMapping("/flight/all")
    public ResponseEntity<List<Flight>> getFlightDetails() {
        log.info("FlightController.getFlightDetails started");
        List<Flight> response = flightDelegate.getFlightDetails();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/flight")
    public ResponseEntity<Flight> findByFlightNumberAndFlightDate(@RequestParam String flightNumber, @RequestParam String flightDate) {
        log.info("FlightController.getFlightDetails started");
        Flight response = flightDelegate.findByFlightNumberAndFlightDate(flightNumber, LocalDate.parse(flightDate));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("/update-flight")
    public ResponseEntity<String> updateFlight(@RequestBody Flight flight) {
        log.info("FlightController.getFlightDetails started");
        String response = flightDelegate.updateFlight(flight);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
