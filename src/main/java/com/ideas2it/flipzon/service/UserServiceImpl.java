package com.ideas2it.flipzon.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.UserDao;
import com.ideas2it.flipzon.model.User;
import com.ideas2it.flipzon.model.Role;
import com.ideas2it.flipzon.model.UserRole;
/**
 * <p>
 * UserService represents CRUD operations for user and get user by id
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleService roleService;

    private final static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);

    public void createAdmin() {
        User user = User.builder()
                .name("ADMIN")
                .email("admin@gmail.com")
                .password(encoder.encode(System.getenv("ADMINPASSWORD")))
                .phoneNumber("9087654321")
        .build();
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().id(1).name(UserRole.ROLE_ADMIN).build());
        user.setRole(roles);
        if (!userDao.existsByName("ADMIN")) {
            userDao.save(user);
        }
    }

    public  boolean checkByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    public  User getByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public User addUser(User user) {
        return userDao.save(user);
    }
}
