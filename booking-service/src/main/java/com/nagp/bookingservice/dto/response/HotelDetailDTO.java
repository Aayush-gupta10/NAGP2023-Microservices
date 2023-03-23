package com.nagp.bookingservice.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class HotelDetailDTO {
    private String hotelName;
    private String city;
    private Integer numberOfRoomsAvailable;
    private String startingPricePerNight;
    private List<RoomDetailDTO> rooms;
}
