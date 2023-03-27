package com.nagp.searchservice.service.impl;

import com.nagp.searchservice.dto.response.FlightDetailDTO;
import com.nagp.searchservice.service.UserClientService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class UserClientServicesImpl implements UserClientService {

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Boolean validateUser(Integer userId) {
        String masterDataFlightUrl = "/user-service/validate-user?userId="+userId;
        InstanceInfo userServiceInstance = eurekaClient.getNextServerFromEureka("user-service", false);
        try {
            ResponseEntity<Boolean> responseEntity = restTemplate.exchange(userServiceInstance.getHomePageUrl() + masterDataFlightUrl,
                    HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), new ParameterizedTypeReference<Boolean>() {
                    });
            return responseEntity.getBody();
        }
        catch (RestClientException ex)
        {
            return false;
        }
    }
}
