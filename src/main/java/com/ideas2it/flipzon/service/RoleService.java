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
 * This class implements role service which have methods for add, get, update and delete the role
 * </p>
 *
 * @author Jeevithakesavaraj
 */

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public void addRoles() {
        List<Role> roles = new ArrayList<>();
        if (!roleDao.existsByName(UserRole.ADMIN)) {
            roles.add(Role.builder().name(UserRole.ADMIN).build());
            roles.add(Role.builder().name(UserRole.CUSTOMER).build());
            roles.add(Role.builder().name(UserRole.DELIVERYPARTNER).build());
            roleDao.saveAll(roles);
        }
    }

}
