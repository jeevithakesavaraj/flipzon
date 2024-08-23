package com.ideas2it.flipzon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dto.RoleDto;

/**
 * <p>
 * This interface has method declaration for add, get, update, delete the role.
 * </p>
 *
 * @author Jeevithakesavaraj
 */

@Service
public interface RoleService {

    /**
     * <p>
     * Add role details
     * </p>
     *
     * @param roleDto   {@link RoleDto}
     * @return RoleDto   saved role detail
     */
    RoleDto addRole(RoleDto roleDto);

    /**
     * <p>
     * Get list of roles
     * </p>
     *
     * @return  List<RoleDto>   list of roles {@link RoleDto}
     */
    List<RoleDto> getRoles();

    /**
     * <p>
     * Get role by Id
     * </p>
     *
     * @param id    Id of the role
     * @return RoleDto  searched role detail
     */
    RoleDto getRoleById(long id);

    /**
     * <p>
     * Update role details by Id
     * </p>
     *
     * @param roleDto   {@link RoleDto}
     * @return RoleDto   updated role detail
     */
    RoleDto updateRole(RoleDto roleDto);

    /**
     * <p>
     * Delete role by id
     * </p>
     * @param id    Id of the role which we have to delete
     */
    void deleteRole(long id);
}
