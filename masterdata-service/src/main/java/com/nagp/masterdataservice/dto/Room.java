package com.nagp.masterdataservice.dto;

import lombok.Data;

@Data
public class Room {

    private Integer roomNum;
    private String roomType;
    private String pricePerNight;
    private String bedType;
    private Boolean breakfastIncluded;
    private Boolean isAvailable;

}
