package com.nagp.usermanager.controller;

import com.nagp.usermanager.domain.models.Customer;
import com.nagp.usermanager.exceptions.UserNotExistException;
import com.nagp.usermanager.service.LoginService;
import com.nagp.usermanager.service.impl.LoginServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;
    @GetMapping("/login")
    public ResponseEntity<Integer> login(@RequestParam String username, @RequestParam String password) {
        log.info("LoginController.login started");
        Integer response = loginService.login(username,password);
        if(response==null)
        {
            throw new UserNotExistException("User not exist");
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/validate-user")
    public ResponseEntity<Boolean> validateUser(@RequestParam Integer userId) {
        log.info("LoginController.validate user started");
        Boolean response = loginService.validateUser(userId);
        if(!response)
        {
            throw new UserNotExistException("User not exist");
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/user-detail")
    public ResponseEntity<Boolean> getUserDetail(@RequestParam Integer userId) {
        log.info("LoginController.gerUserDetail started");
        Customer response = loginService.getUserDetail(userId);
        if(response==null)
        {
            throw new UserNotExistException("User not exist");
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
