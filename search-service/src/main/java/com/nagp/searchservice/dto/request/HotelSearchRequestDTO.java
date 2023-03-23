package com.nagp.searchservice.dto.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class HotelSearchRequestDTO {
    @NotNull(message = "hotelName is a mandatory field")
    private String hotelName;
    @NotNull(message = "city is a mandatory field")
    private String city;
    @NotNull(message = "checkInDate is a mandatory field")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkInDate;
    @NotNull(message = "checkOutDate is a mandatory field")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkOutDate;
}
