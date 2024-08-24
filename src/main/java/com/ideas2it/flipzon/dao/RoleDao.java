package com.ideas2it.flipzon.dao;

import java.util.List;

import com.ideas2it.flipzon.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.flipzon.model.Role;

/**
 * <p>
 * It performs CRUD operations for role.
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@RestController
public interface RoleDao extends JpaRepository<Role, Long> {
    boolean existsByName(UserRole userRole);
}
