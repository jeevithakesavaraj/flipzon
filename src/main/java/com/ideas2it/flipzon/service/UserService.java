package com.ideas2it.flipzon.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ideas2it.flipzon.model.Role;
import com.ideas2it.flipzon.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.UserDao;
import com.ideas2it.flipzon.model.User;


/**
 * <p>
 * UserService represents CRUD operations for user and get user by id
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    private final static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);
    /**
     * <p>
     * Create Admin
     * </p>
     */
    public void createAdmin() {
        User user = User.builder()
                .name("ADMIN")
                .email("admin@gmail.com")
                .password(encoder.encode(System.getenv("ADMINPASSWORD")))
                .phoneNumber("9087654321")
        .build();
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().id(1).name(UserRole.ROLE_ADMIN).build());
        user.setRoles(roles);
        if (!userDao.existsByName("ADMIN")) {
            userDao.save(user);
        }
    }

    /**
     * <p>
     * Checks if the email Id is present or not
     * </p>
     * @param email  EmailId of the user
     * @return boolean  If the emailId is present, return true or else false
     */
    public  boolean checkByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    /**
     * <p>
     * Get user by emailID
     * </p>
     * @param email   EmailId of the user
     * @return User {@link User}
     */
    public  User getByEmail(String email) {
        return userDao.findByEmail(email);
    }

    /**
     * Get all users
     *
     * @return List of users
     */
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
    /**
     * <p>
     * Add user to the database
     * </p>
     * @param user   {@link User}
     * @return User {@link User}
     */
    public User addUser(User user) {
        return userDao.save(user);
    }
}
