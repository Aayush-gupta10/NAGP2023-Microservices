package com.nagp.masterdataservice.service.impl;

import com.nagp.masterdataservice.dto.Hotel;
import com.nagp.masterdataservice.dto.Room;
import com.nagp.masterdataservice.service.HotelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    @Override
    public List<Hotel> getHotelDetails() {
        Hotel Hotel = new Hotel();
        Hotel.setHotelName("STAY STAR");
        Hotel.setNumberOfRooms(10);
        Hotel.setCity("DELHI");
        Hotel.setStartingPricePerNight("12345");
        Hotel.setRooms(List.of(getRoom()));
        return List.of(Hotel);
    }

    private static Room getRoom() {
        Room room = new Room();
        room.setRoomNum(101);
        room.setRoomType("Standard");
        room.setBedType("King");
        room.setBreakfastIncluded(true);
        room.setIsAvailable(true);
        room.setPricePerNight("12345");
        return room;
    }
}
