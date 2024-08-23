package com.ideas2it.flipzon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MyException.class)
    public ResponseEntity<String> handleAlreadyPresent(MyException myException) {
        return new ResponseEntity<>(myException.getMessage(), HttpStatus.ALREADY_REPORTED);
    }
}
