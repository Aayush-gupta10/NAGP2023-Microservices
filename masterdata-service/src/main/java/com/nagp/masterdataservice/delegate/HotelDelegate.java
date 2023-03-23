package com.nagp.masterdataservice.delegate;

import com.nagp.masterdataservice.dto.Hotel;
import com.nagp.masterdataservice.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HotelDelegate {

    @Autowired
    private HotelService hotelService;

    public List<Hotel> getHotelDetails() {
        return hotelService.getHotelDetails();
    }

    public Hotel findByHotelNameAndCity(String hotelName, String city) {
        return hotelService.findByHotelNameAndCity(hotelName, city);
    }

    public String updateHotel(Hotel hotel) {
        return hotelService.updateHotel(hotel);
    }
}
