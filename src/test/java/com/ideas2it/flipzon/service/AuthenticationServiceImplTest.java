package com.ideas2it.flipzon.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ideas2it.flipzon.dao.CustomerDao;
import com.ideas2it.flipzon.dao.UserDao;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Role;
import com.ideas2it.flipzon.model.User;
import com.ideas2it.flipzon.model.UserRole;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private CustomerDao customerDao;

    @InjectMocks
    private UserServiceImpl userService;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private User user;

    private Customer customer;

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
                .id(1)
                .user(user)
                .build();
    }

    @Test
    void testRegisterCustomer() {
        when(userDao.save(any(User.class))).thenReturn(user);
        when(customerDao.save(any(Customer.class))).thenReturn(customer);
        User savedUser = userService.addUser(user);
        Customer savedCustomer = customerService.addCustomer(customer);
        assertEquals(1, savedCustomer.getId());
    }
}
