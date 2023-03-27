package com.nagp.notificationservice.dto;

import lombok.Data;

@Data
public class Customer {
    private Integer id;
    private String name;
    private String password;
    private String contactNo;
    private String email;
    private String address;
}
