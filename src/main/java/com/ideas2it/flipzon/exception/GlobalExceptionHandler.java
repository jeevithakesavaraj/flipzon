package com.ideas2it.flipzon.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ideas2it.flipzon.common.APIResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MyException.class)
    public ResponseEntity<APIResponse> handleAlreadyPresent(MyException myException) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.CONFLICT.value());
        apiResponse.setData(myException.getMessage());
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @ExceptionHandler(OutOfStock.class)
    public ResponseEntity<APIResponse> handleStockNotAvailable(OutOfStock outOfStock) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(outOfStock.getMessage());
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @ExceptionHandler(EmptyCart.class)
    public ResponseEntity<APIResponse> handleCartIsEmpty(EmptyCart emptyCart) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(emptyCart.getMessage());
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String,String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse> accessDeniedException(AccessDeniedException accessDeniedException) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        apiResponse.setData(resourceNotFoundException.getMessage());
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
