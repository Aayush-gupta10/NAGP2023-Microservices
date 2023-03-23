package com.nagp.usermanager.domain.models;

import lombok.Data;

@Data
public class Admin {

    private long adminID;
    private String adminName;
    private String adminPassword;
}
