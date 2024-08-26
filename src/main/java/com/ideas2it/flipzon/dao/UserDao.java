package com.ideas2it.flipzon.dao;

import com.ideas2it.flipzon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    User findByEmail(String email);
}
