package com.ideas2it.flipzon.service;

import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.model.User;

@Service
public interface UserService {

    /**
     * <p>
     * Create admin
     * </p>
     */
    void createAdmin();

    /**
     * <p>
     * Check if the user is present or not by their emailId
     * </p>
     * @param email  EmailId of the user
     * @return boolean If user is present, return true or false
     */
    boolean checkByEmail(String email);

    /**
     * <p>
     * Get User by EmailId
     * </p>
     * @param email EmailId of the user
     * @return User {@link User}
     */
    User getByEmail(String email);

    /**
     * <p>
     * Add user to the database
     * </p>
     * @param user  {@link User}
     * @return savedUser {@link User}
     */
    User addUser(User user);
}
