package com.nagp.usermanager.domain.models;

import lombok.Data;

import java.util.List;

@Data
public class Customer {
    private Long id;
    private String name;
    private String password;
    private String contactNo;
    private String email;
    private CustomerAddress address;
    private List<Long> bookingIds;
}
