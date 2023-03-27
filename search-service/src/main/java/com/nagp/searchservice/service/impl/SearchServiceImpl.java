package com.nagp.searchservice.service.impl;

import com.nagp.searchservice.dto.request.FlightSearchRequestDTO;
import com.nagp.searchservice.dto.request.HotelSearchRequestDTO;
import com.nagp.searchservice.dto.response.FlightDetailDTO;
import com.nagp.searchservice.dto.response.HotelDetailDTO;
import com.nagp.searchservice.service.SearchService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<FlightDetailDTO> searchFlight(FlightSearchRequestDTO flightSearchRequestDTO) {
        List<FlightDetailDTO> flightDetailDTOList = getFlightDetail();
        List<FlightDetailDTO> filteredFlightDetailDTOList = flightDetailDTOList.stream().filter(flightDetailDTO ->
                        flightSearchRequestDTO.getArrivalLocation().equals(flightDetailDTO.getArrivalLocation()) &&
                                flightSearchRequestDTO.getDepartureLocation().equals(flightDetailDTO.getDepartureLocation()) &&
                                flightSearchRequestDTO.getFlightClass().name().equals(flightDetailDTO.getFlightClass()) &&
                                flightSearchRequestDTO.getFlightDate().equals(flightDetailDTO.getFlightDate()))
                .collect(Collectors.toList());

        return filteredFlightDetailDTOList;
    }

    @Override
    public List<HotelDetailDTO> searchHotel(HotelSearchRequestDTO hotelSearchRequestDTO) {
        List<HotelDetailDTO> hotelDetailDTOList = getHotelDetail();
        List<HotelDetailDTO> filteredHotelDetailDTOList = hotelDetailDTOList.stream().filter(hotelDetailDTO ->
                        hotelSearchRequestDTO.getHotelName().equals(hotelDetailDTO.getHotelName()) &&
                                hotelSearchRequestDTO.getCity().equals(hotelDetailDTO.getCity()))
                .collect(Collectors.toList());
        return filteredHotelDetailDTOList;
    }

    @HystrixCommand(fallbackMethod = "getFallbackForFlightMasterdata")
    private List<FlightDetailDTO> getFlightDetail() {
        String masterDataFlightUrl = "/master-data-service/flight/all";
        InstanceInfo masterDataInstance = eurekaClient.getNextServerFromEureka("master-data-service", false);
        ResponseEntity<List<FlightDetailDTO>> responseEntity = restTemplate.exchange(masterDataInstance.getHomePageUrl() + masterDataFlightUrl,
                HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), new ParameterizedTypeReference<List<FlightDetailDTO>>() {});
        return responseEntity.getBody();
    }

    @HystrixCommand(fallbackMethod = "getFallbackForMasterdata")
    private List<HotelDetailDTO> getHotelDetail() {
        String masterDataHotelUrl = "/master-data-service/hotel/all";
        InstanceInfo masterDataInstance = eurekaClient.getNextServerFromEureka("master-data-service", false);
        ResponseEntity<List<HotelDetailDTO>> responseEntity = restTemplate.exchange(masterDataInstance.getHomePageUrl() + masterDataHotelUrl,
                HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), new ParameterizedTypeReference<List<HotelDetailDTO>>() {});
        return responseEntity.getBody();
    }

    private List<FlightDetailDTO> getFallbackForFlightMasterdata() {
        System.out.println("Master-data Service is down!!! fallback route enabled...");
        log.info("CIRCUIT BREAKER ENABLED!!!No Response From Master-data Service at this moment. Service will be back shortly - " + new Date());
        return new ArrayList<>();
    }
}
