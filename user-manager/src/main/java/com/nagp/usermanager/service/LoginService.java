package com.nagp.usermanager.service;

import com.nagp.usermanager.domain.models.Customer;

public interface LoginService {

    Integer login(String username, String password);

    Boolean validateUser(Integer userId);

    Customer getUserDetail(Integer userId);
}
