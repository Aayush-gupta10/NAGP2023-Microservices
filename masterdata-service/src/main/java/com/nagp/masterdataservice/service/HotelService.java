package com.nagp.masterdataservice.service;

import com.nagp.masterdataservice.dto.Hotel;

import java.util.List;

public interface HotelService {
    List<Hotel> getHotelDetails();

    Hotel findByHotelNameAndCity(String hotelName, String city);

    String updateHotel(Hotel hotel);
}
