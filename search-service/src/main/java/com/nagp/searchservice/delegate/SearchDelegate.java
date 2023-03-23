package com.nagp.searchservice.delegate;

import com.nagp.searchservice.dto.request.FlightSearchRequestDTO;
import com.nagp.searchservice.dto.request.HotelSearchRequestDTO;
import com.nagp.searchservice.dto.response.FlightDetailDTO;
import com.nagp.searchservice.dto.response.HotelDetailDTO;
import com.nagp.searchservice.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SearchDelegate {

    @Autowired
    private SearchService searchService;

    public List<FlightDetailDTO> searchFlight(FlightSearchRequestDTO flightSearchRequestDTO) {
        log.info("SearchDelegate.searchFlight started");
        return searchService.searchFlight(flightSearchRequestDTO);
    }

    public List<HotelDetailDTO> searchHotel(HotelSearchRequestDTO hotelSearchRequestDTO) {
        log.info("SearchDelegate.searchFlight started");
        return searchService.searchHotel(hotelSearchRequestDTO);
    }
}
