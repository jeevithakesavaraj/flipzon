package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dao.UserDao;
import com.ideas2it.flipzon.dto.UserDto;
import com.ideas2it.flipzon.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public void createAdmin() {
        User user = User.builder()
                .name("ADMIN")
                .email("admin@gmail.com")
                .password("admin@123")
                .phoneNumber("9087654321")
        .build();
        if (!userDao.existsByName("ADMIN")) {
            userDao.save(user);
        }
    }

    public  boolean checkByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    public void createUser(User user) {
        userDao.save(user);
    }
}
