package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.RoleDto;
import com.ideas2it.flipzon.model.Role;

public class RoleMapper {

    public static Role convertDtoToEntity(RoleDto roleDto) {
        return Role.builder()
                .name(roleDto.getName())
                .build();
    }

    public static RoleDto convertEntityToDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
