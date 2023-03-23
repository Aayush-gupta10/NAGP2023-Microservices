package com.nagp.searchservice.service;

import com.nagp.searchservice.dto.request.FlightSearchRequestDTO;
import com.nagp.searchservice.dto.request.HotelSearchRequestDTO;
import com.nagp.searchservice.dto.response.FlightDetailDTO;
import com.nagp.searchservice.dto.response.HotelDetailDTO;

import java.util.List;

public interface SearchService {
    List<FlightDetailDTO> searchFlight(FlightSearchRequestDTO flightSearchRequestDTO);

    List<HotelDetailDTO> searchHotel(HotelSearchRequestDTO hotelSearchRequestDTO);
}
