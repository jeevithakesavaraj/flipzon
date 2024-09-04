package com.ideas2it.flipzon.service;

import java.time.LocalDate;
import java.util.Date;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.ideas2it.flipzon.dao.UserDao;
import com.ideas2it.flipzon.model.Role;
import com.ideas2it.flipzon.model.User;
import com.ideas2it.flipzon.model.UserRole;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

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
    }

    @Test
    void testAddEmployee() {
        when(userDao.save(any(User.class))).thenReturn(user);
        User savedUser = userService.addUser(user);
        assertEquals("Jeevitha", savedUser.getName());
    }

    @Test
    void testGetByEmail() {
        when(userDao.findByEmail("jeevitha@gmail.com")).thenReturn(user);
        User savedUser = userService.getByEmail(user.getEmail());
        assertThat(savedUser).isNotNull();
    }

}
