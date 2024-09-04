package com.ideas2it.flipzon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ideas2it.flipzon.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.ideas2it.flipzon.dao.AddressDao;
import com.ideas2it.flipzon.dto.AddressDto;
import com.ideas2it.flipzon.mapper.AddressMapper;
import com.ideas2it.flipzon.model.Address;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.common.APIResponse;

class AddressServiceTest {

    @Mock
    private AddressDao addressDao;

    @Mock
    private CustomerService customerService;

    @Mock
    private APIResponse apiResponse;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;
    private AddressDto addressDto;
    private Customer customer;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = Customer.builder()
                .id(1L)
                .user(user)
                .build();
        user = User.builder()
                .id(1L)
                .name("Hermoine")
                .email("hermoine@xampp.com")
                .build();
        address = Address.builder()
                .id(1L)
                .addressLine("123 Main St")
                .city("Salem")
                .pinCode("636010")
                .isDeleted(false)
                .build();
        addressDto = AddressMapper.convertEntityToDto(address);
    }

    @Test
    void testAddAddress() {
        when(customerService.isCustomerPresent(customer.getId())).thenReturn(true);
        when(customerService.getCustomerById(customer.getId())).thenReturn(customer);
        when(addressDao.save(any(Address.class))).thenReturn(address);
        when(apiResponse.getStatus()).thenReturn(HttpStatus.OK.value());
        APIResponse response = addressService.addAddress(1L, addressDto);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testUpdateAddressByCustomerId_Success() {
        when(addressDao.existsByIdAndCustomerIdAndIsDeletedFalse(anyLong(), anyLong())).thenReturn(true);
        when(addressDao.findById(anyLong())).thenReturn(Optional.of(address));
        when(addressDao.save(any(Address.class))).thenReturn(address);
        when(apiResponse.getStatus()).thenReturn(HttpStatus.OK.value());
        APIResponse response = addressService.updateAddressByCustomerId(1L, addressDto);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(addressDao, times(1)).save(any(Address.class));
    }

    @Test
    void testDeleteAddressByCustomerId_Success() {
        when(addressDao.existsByIdAndCustomerIdAndIsDeletedFalse(anyLong(), anyLong())).thenReturn(true);
        when(addressDao.findById(anyLong())).thenReturn(Optional.of(address));
        when(apiResponse.getStatus()).thenReturn(HttpStatus.OK.value());
        APIResponse response = addressService.deleteAddressByCustomerId(1L, 1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(addressDao, times(1)).save(any(Address.class));
    }

    @Test
    void testGetAddressById() {
        when(addressDao.findById(anyLong())).thenReturn(Optional.of(address));
        Address result = addressService.getAddressById(1L);
        assertNotNull(result);
        assertEquals(address.getId(), result.getId());
        verify(addressDao, times(1)).findById(anyLong());
    }


    @Test
    void getAddressesByCustomerId() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        when(customerService.isCustomerPresent(anyLong())).thenReturn(true);
        when(addressDao.findByCustomerIdAndIsDeletedFalse(anyLong())).thenReturn(addresses);
        when(apiResponse.getStatus()).thenReturn(HttpStatus.OK.value());
        APIResponse response = addressService.getAddressesByCustomerId(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(addressDao, times(1)).findByCustomerIdAndIsDeletedFalse(anyLong());
    }
}