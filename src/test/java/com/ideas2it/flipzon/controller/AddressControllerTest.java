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
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ideas2it.flipzon.common.APIResponse;
import com.ideas2it.flipzon.dto.AddressDto;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.User;
import com.ideas2it.flipzon.service.AddressService;

@ExtendWith(MockitoExtension.class)
public class AddressControllerTest {

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    AddressDto addressDto;

    APIResponse apiResponse;

    Customer customer;

    User user;

    @BeforeEach
    void setUp() {
        apiResponse = APIResponse.builder()
                .status(200)
                .data("ash")
                .build();

        addressDto = AddressDto.builder()
                .addressLine("10, Main st, Egmore")
                .city("Chennai")
                .pinCode("600028")
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
    }

    @Test
    void addAddress() {
        when(addressService.addAddress(1L, addressDto)).thenReturn(apiResponse);
        ResponseEntity<APIResponse> response = addressController.addAddress(1L, addressDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(addressService, times(1)).addAddress(1L, addressDto);
    }

    @Test
    void getAddressesByCustomerId() {
        when(addressService.getAddressesByCustomerId(1L)).thenReturn(apiResponse);
        ResponseEntity<APIResponse> response = addressController.getAddressesByCustomerId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(addressService, times(1)).getAddressesByCustomerId(1L);
    }

    @Test
    void updateAddressByCustomerId() {
        when(addressService.updateAddressByCustomerId(1L, addressDto)).thenReturn(apiResponse);
        ResponseEntity<APIResponse> response = addressController.updateAddressByCustomerId(1L, addressDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(addressService, times(1)).updateAddressByCustomerId(1L, addressDto);
    }

    @Test
    void deleteAddressByCustomerId() {
        when(addressService.deleteAddressByCustomerId(anyLong(), anyLong())).thenReturn(apiResponse);
        ResponseEntity<APIResponse> response = addressController.deleteAddressByCustomerId(anyLong(), anyLong());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(addressService, times(1)).deleteAddressByCustomerId(anyLong(), anyLong());
    }
}
