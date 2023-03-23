package com.nagp.masterdataservice.controller;

import com.nagp.masterdataservice.delegate.HotelDelegate;
import com.nagp.masterdataservice.dto.Hotel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/hotel")
    public ResponseEntity<Hotel> findByHotelNameAndCity(@RequestParam String hotelName, @RequestParam String city) {
        log.info("HotelController.getHotelDetails started");
        Hotel response = hotelDelegate.findByHotelNameAndCity(hotelName, city);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("/update-hotel")
    public ResponseEntity<String> updateHotel(Hotel hotel) {
        log.info("HotelController.getHotelDetails started");
        String response = hotelDelegate.updateHotel(hotel);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
