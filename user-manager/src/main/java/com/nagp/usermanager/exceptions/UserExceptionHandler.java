package com.nagp.usermanager.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Order(1)
public class UserExceptionHandler {




    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<Error> handleConverterErrors(UserNotExistException exception) {
        log.error(exception.getMessage(), exception);
        String message = exception.getMessage();
        Error error = new Error(message);
        return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    }


}
