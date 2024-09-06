package com.ideas2it.flipzon.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ideas2it.flipzon.common.APIResponse;
import com.ideas2it.flipzon.dto.AuthenticationResponse;
import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.dto.DeliveryPersonDto;
import com.ideas2it.flipzon.dto.LoginDto;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.User;
import com.ideas2it.flipzon.service.AuthenticationService;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    APIResponse apiResponse;

    LoginDto loginDto;

    CustomerDto customerDto;

    DeliveryPersonDto deliveryPersonDto;

    Customer customer;

    User user;

    AuthenticationResponse authenticationResponse;

    @BeforeEach
    void setUp() {
        apiResponse = APIResponse.builder()
                .status(200)
                .data("")
                .build();

        loginDto = LoginDto.builder()
                .email("admin@gmail.com")
                .password("admin@123")
                .build();

        customerDto = CustomerDto.builder()
                .id(1L)
                .name("Aadhini")
                .email("aadhini@gmail.com")
                .password("aadhini@123")
                .phoneNumber("9012345678")
                .build();

        customer = Customer.builder()
                .id(1L)
                .user(user)
                .isDeleted(false)
                .build();

        user = User.builder()
                .id(1L)
                .name("Hermoine")
                .email("hermoine@xampp.com")
                .build();

        deliveryPersonDto = DeliveryPersonDto.builder()
                .name("Gowtham")
                .id(1L)
                .phoneNumber("9012345678")
                .password("Gowtham@123")
                .idProof("123456qwerty7890")
                .build();
    }

    @Test
    void registerCustomer() {
        when(authenticationService.registerCustomer(customerDto)).thenReturn(apiResponse);
        ResponseEntity<APIResponse> response = authenticationController.registerCustomer(customerDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(authenticationService, times(1)).registerCustomer(customerDto);
    }

    @Test
    void registerDeliveryPerson() {
        when(authenticationService.registerDeliveryPerson(deliveryPersonDto)).thenReturn(apiResponse);
        ResponseEntity<APIResponse> response = authenticationController.registerDeliveryPerson(deliveryPersonDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(authenticationService, times(1)).registerDeliveryPerson(deliveryPersonDto);
    }

    @Test
    void login() {
        when(authenticationService.authenticate(loginDto)).thenReturn(authenticationResponse);
        ResponseEntity<AuthenticationResponse> response = authenticationController.login(loginDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authenticationResponse, response.getBody());
        verify(authenticationService, times(1)).authenticate(loginDto);
    }
}
