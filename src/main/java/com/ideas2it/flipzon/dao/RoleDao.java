package com.ideas2it.flipzon.dao;

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
    /**
     * <p>
     * Checks if the name of the role is present or not
     * </p>
     *
     * @param userRole   {@link UserRole}
     * @return  boolean    If the name of the role present, return true or else return false
     */
    boolean existsByName(UserRole userRole);

    /**
     * <p>
     * Get the role by name
     * </p>
     * @param userRole {@link UserRole}
     * @return  Role    role which have searched
     */
    Role findByName(UserRole userRole);
}
