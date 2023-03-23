package com.nagp.masterdataservice.service.impl;

import com.nagp.masterdataservice.dto.Flight;
import com.nagp.masterdataservice.dto.Hotel;
import com.nagp.masterdataservice.dto.Room;
import com.nagp.masterdataservice.service.HotelService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    private final static List<Hotel> hotels = new ArrayList<>();

    @PostConstruct
    public void createHotel(){
        Hotel Hotel = new Hotel();
        Hotel.setHotelName("STAY STAR");
        Hotel.setNumberOfRoomsAvailable(10);
        Hotel.setCity("DELHI");
        Hotel.setStartingPricePerNight("12345");
        Hotel.setRooms(List.of(getRoom()));
        hotels.add(Hotel);
    }
    @Override
    public List<Hotel> getHotelDetails() {
        return hotels;
    }

    @Override
    public Hotel findByHotelNameAndCity(String hotelName, String city) {
        return hotels.stream().filter(hotel -> hotelName.equals(hotel.getHotelName()) && city.equals(hotel.getCity())).findFirst().get();
    }

    @Override
    public String updateHotel(Hotel hotel) {
        hotels.get(0).setNumberOfRoomsAvailable(hotel.getNumberOfRoomsAvailable());
        return null;
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
