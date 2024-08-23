package com.ideas2it.flipzon.dao;

import com.ideas2it.flipzon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    /**
     * Retrieves all active users.
     *
     * @return A list of active users.
     */
    List<User> findByIsDeletedFalse();
}
