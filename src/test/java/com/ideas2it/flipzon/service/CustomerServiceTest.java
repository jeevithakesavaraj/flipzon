package com.ideas2it.flipzon.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ideas2it.flipzon.dao.CustomerDao;
import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Role;
import com.ideas2it.flipzon.model.User;
import com.ideas2it.flipzon.model.UserRole;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;

    private User user;

    private List<Customer> customers = new ArrayList<>();

    @BeforeEach
    void setUp() {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.of(2024, 9, 2);
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        Set<Role> roles = new HashSet<>();
        UserRole userRole = UserRole.ROLE_CUSTOMER;
        Role role = new Role(1, userRole);
        roles.add(role);
        user = new User(1, "Jeevitha",  "jeevitha@gmail.com", "jeevith@123", "9012345678", roles, date);
        customer = Customer.builder()
                .id(1L)
                .user(user)
                .build();
        customers.add(customer);
    }

    @Test
    void testAddCustomer() {
        when(customerDao.save(any(Customer.class))).thenReturn(customer);
        Customer savedCustomer = customerService.addCustomer(customer);
        assertEquals(1L, savedCustomer.getId());
    }

    @Test
    void testGetCustomerByIdSuccess() {
        when(customerDao.findByIdAndIsDeletedFalse(1L)).thenReturn(customer);
        Customer retrivedCustomer = customerService.getCustomerById(1L);
        assertEquals(1L, retrivedCustomer.getId());
    }

    @Test
    void testGetCustomerByIdFailure() {
        when(customerDao.findByIdAndIsDeletedFalse(1L)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            customerService.getCustomerById(1L);
        });
    }

    @Test
    void testGetAllCustomers() {
        when(customerDao.findAll()).thenReturn(customers);
        List<CustomerDto> customerDtos = customerService.getAllCustomers();
        assertEquals(1, customerDtos.size());
    }

    @Test
    void testIsCustomerPresent() {
        when(customerDao.existsByIdAndIsDeletedFalse(1L)).thenReturn(true);
        boolean isPresent = customerService.isCustomerPresent(1L);
        assertEquals(true, isPresent);
    }
}
