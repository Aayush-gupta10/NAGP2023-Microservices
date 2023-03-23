package com.nagp.usermanager.domain.models;

import lombok.Data;

@Data
public class CustomerAddress {
    private String addressLine;
    private String city;
    private String state;
    private String zip;
    private String country;
}
