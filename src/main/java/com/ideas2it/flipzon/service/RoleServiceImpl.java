package com.ideas2it.flipzon.service;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.flipzon.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.RoleDao;
import com.ideas2it.flipzon.model.Role;
import com.ideas2it.flipzon.dto.RoleDto;
import com.ideas2it.flipzon.mapper.RoleMapper;

/**
 * <p>
 * This class implements role service which have methods for add, get, update and delete the role
 * </p>
 *
 * @author Jeevithakesavaraj
 */

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public RoleDto addRoles() {
        Role.builder().name(UserRole.ADMIN);

        Role savedRole = roleDao.save(RoleMapper.convertDtoToEntity(roleDto));
        return RoleMapper.convertEntityToDto(savedRole);
    }

    @Override
    public List<RoleDto> getRoles() {
        List<Role> roles = roleDao.findByIsDeletedFalse();
        List<RoleDto> roleDtos = new ArrayList<>();
        for (Role role: roles) {
            roleDtos.add(RoleMapper.convertEntityToDto(role));
        }
        return roleDtos;
    }

    @Override
    public RoleDto getRoleById(long id) {
        Role role = roleDao.findByIdAndIsDeletedFalse(id);
        return RoleMapper.convertEntityToDto(role);
    }

    @Override
    public RoleDto updateRole(RoleDto roleDto) {
        Role role = roleDao.findByIdAndIsDeletedFalse(roleDto.getId());
        role.setName(roleDto.getName());
        Role updatedRole = roleDao.save(role);
        return RoleMapper.convertEntityToDto(updatedRole);
    }

    @Override
    public void deleteRole(long id) {
        Role role = roleDao.findByIdAndIsDeletedFalse(id);
        role.setDeleted(true);
        roleDao.save(role);
    }

}
