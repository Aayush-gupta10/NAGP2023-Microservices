package com.nagp.usermanager.service.impl;

import com.nagp.usermanager.domain.models.Customer;
import com.nagp.usermanager.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private static final List<Customer> customers = new ArrayList<>();

    @PostConstruct
    public void createCustomer(){
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("User_1");
        customer.setEmail("user1@nagp.com");
        customer.setPassword("password");
        customer.setAddress("Street 123, US");
        customer.setContactNo("1234567890");
        customers.add(customer);
    }
    @Override
    public Integer login(String username, String password) {
        Optional<Customer> user = customers.stream().filter(customer -> username.equals(customer.getName()) && password.equals(customer.getPassword())).findFirst();
        if(user.isPresent())
        {
            return user.get().getId();
        }
        return null;
    }

    @Override
    public Boolean validateUser(Integer userId) {

        Optional<Customer> user = customers.stream().filter(customer -> userId.equals(customer.getId())).findFirst();
        if(user.isPresent())
        {
            return true;
        }
        return false;
    }

    @Override
    public Customer getUserDetail(Integer userId) {
        Optional<Customer> user = customers.stream().filter(customer -> userId.equals(customer.getId())).findFirst();
        if(user.isPresent())
        {
            return user.get();
        }
        return null;
    }
}
