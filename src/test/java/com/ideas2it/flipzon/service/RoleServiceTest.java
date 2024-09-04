package com.ideas2it.flipzon.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import com.ideas2it.flipzon.dao.RoleDao;
import com.ideas2it.flipzon.model.Role;
import com.ideas2it.flipzon.model.UserRole;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleDao roleDao;

    @InjectMocks
    private RoleService roleService;

    private Role firstRole;
    private Role secondRole;
    private Role thirdRole;
    private List<Role> roles = new ArrayList<>();
    @BeforeEach
    void setUp() {
        firstRole = Role.builder().name(UserRole.ROLE_ADMIN).build();
        secondRole = Role.builder().name(UserRole.ROLE_CUSTOMER).build();
        thirdRole = Role.builder().name(UserRole.ROLE_DELIVERYPERSON).build();
        roles.add(firstRole);
        roles.add(secondRole);
        roles.add(thirdRole);
    }

    @Test
    void testAddRolesSuccess() {
        roleDao.saveAll(roles);
        roleService.addRoles();
    }

    @Test
    void testAddRolesFailure() {
        when(roleDao.existsByName(UserRole.ROLE_ADMIN)).thenReturn(true);
        roleService.addRoles();
    }

    @Test
    void testGetRoleByNameSuccess() {
        when(roleDao.findByName(UserRole.ROLE_ADMIN)).thenReturn(firstRole);
        Role role = roleService.getRoleByName(UserRole.ROLE_ADMIN);
        assertThat(role).isNotNull();
    }

    @Test
    void testGetRoleByNameFaliure() {
        when(roleDao.findByName(UserRole.ROLE_ADMIN)).thenReturn(null);
        Role role = roleService.getRoleByName(UserRole.ROLE_ADMIN);
        assertThat(role).isNull();
    }
}
