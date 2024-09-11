package com.ideas2it.flipzon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.flipzon.model.User;

/**
 * <p>
 * UserDao have CRUD operations for user to the database.
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {

    /**
     * <p>
     * Checks if the name is present or not
     * </p>
     * @param name    Name of the user
     * @return boolean If the name is present, return true or else false
     */
    boolean existsByName(String name);

    /**
     * <p>
     * Checks if the email Id is present or not
     * </p>
     * @param email  EmailId of the user
     * @return boolean  If the emailId is present, return true or else false
     */
    boolean existsByEmail(String email);

    /**
     * <p>
     * Get user by emailID
     * </p>
     * @param email   EmailId of the user
     * @return User {@link User}
     */
    User findByEmail(String email);

}
