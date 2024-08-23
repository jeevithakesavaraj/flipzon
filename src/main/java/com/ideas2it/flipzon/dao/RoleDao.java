package com.ideas2it.flipzon.dao;

import java.util.List;

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
     *    Retrieves all the roles by checking their boolean values
     * </p>
     *
     * @return List<Role>  list of roles
     */
    List<Role> findByIsDeletedFalse();

    /**
     * <p>
     *     Retrieves single role by role Id
     * </p>
     *
     * @param id    Id of the role
     * @return Role    role details which we have searched
     */
    Role findByIdAndIsDeletedFalse(long id);
}
