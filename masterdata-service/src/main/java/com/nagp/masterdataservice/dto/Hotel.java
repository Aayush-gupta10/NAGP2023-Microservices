package com.nagp.masterdataservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Hotel {

    private String hotelName;
    private String city;
    private Integer numberOfRooms;
    private String startingPricePerNight;
    private List<Room> rooms;
}
