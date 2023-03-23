package com.nagp.masterdataservice.controller;

import com.nagp.masterdataservice.delegate.HotelDelegate;
import com.nagp.masterdataservice.dto.Hotel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class HotelController {

    @Autowired
    private HotelDelegate hotelDelegate;

    @GetMapping("/hotel/all")
    public ResponseEntity<List<Hotel>> getHotelDetails() {
        log.info("HotelController.getHotelDetails started");
        List<Hotel> response = hotelDelegate.getHotelDetails();
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
