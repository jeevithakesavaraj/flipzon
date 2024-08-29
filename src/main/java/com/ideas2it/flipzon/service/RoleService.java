package com.ideas2it.flipzon.service;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.flipzon.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.RoleDao;
import com.ideas2it.flipzon.model.Role;

/**
 * <p>
 * This class implements role service which have methods for add, get the role
 * </p>
 *
 * @author Jeevithakesavaraj
 */

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    /**
     * <p>
     * Add the roles
     * </p>
     */
    public void addRoles() {
        List<Role> roles = new ArrayList<>();
        if (!roleDao.existsByName(UserRole.ROLE_ADMIN)) {
            roles.add(Role.builder().name(UserRole.ROLE_ADMIN).build());
            roles.add(Role.builder().name(UserRole.ROLE_CUSTOMER).build());
            roles.add(Role.builder().name(UserRole.ROLE_DELIVERYPERSON).build());
            roleDao.saveAll(roles);
        }
    }

    /**
     * <p>
     * Get the role by name
     * </p>
     * @param userRole    {@link UserRole}
     * @return  Role     Role which we have searched
     */
    public Role getRoleByName(UserRole userRole) {
        return roleDao.findByName(userRole);
    }
}
