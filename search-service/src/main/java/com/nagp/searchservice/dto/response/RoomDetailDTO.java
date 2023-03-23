package com.nagp.searchservice.dto.response;

import lombok.Data;

@Data
public class RoomDetailDTO {
    private Integer roomNum;
    private String roomType;
    private String pricePerNight;
    private String bedType;
    private Boolean breakfastIncluded;
    private Boolean isAvailable;
}
