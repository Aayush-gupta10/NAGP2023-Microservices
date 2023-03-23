package com.nagp.masterdataservice.controller;

import com.nagp.masterdataservice.delegate.FlightDelegate;
import com.nagp.masterdataservice.dto.Flight;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
