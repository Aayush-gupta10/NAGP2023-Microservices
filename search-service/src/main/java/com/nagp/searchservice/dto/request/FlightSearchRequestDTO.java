package com.nagp.searchservice.dto.request;

import com.nagp.searchservice.enums.FlightClass;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;

@Data
public class FlightSearchRequestDTO {

    @NotNull(message = "departureLocation is a mandatory field")
    private String departureLocation;
    @NotNull(message = "arrivalLocation is a mandatory field")
    private String arrivalLocation;
    @NotNull(message = "flightClass is a mandatory field")
    private FlightClass flightClass;
    @NotNull(message = "flightDate is a mandatory field")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate flightDate;
}
