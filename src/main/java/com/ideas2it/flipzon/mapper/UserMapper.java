package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.UserDto;
import com.ideas2it.flipzon.model.User;

/**
 * <p>
 *  UserMapper converts all details from Entity to Dto and Vice versa.
 * </p>
 *
 * @author Gowthamraj
 */
public class UserMapper {
    /**
     * Converts a User entity to a UserDto.
     *
     * @param user The User entity to convert.
     * @return The corresponding UserDto.
     */
    public static UserDto convertEntityToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .role_id()
                .build();
    }

    /**
     * Converts a UserDto to a User entity.
     *
     * @param userDto The UserDto to convert.
     * @return The corresponding User entity.
     */
    public static User covertDtoToEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .build();
    }

    /**
     * Helper method to get the primary role ID from a set of roles.
     *
     * @param roles The set of roles assigned to the user.
     * @return The primary role ID, or 0 if no roles exist.
     */
//    private static long getPrimaryRoleId(Set<Role> roles) {
//        return roles != null && !roles.isEmpty()
//                ? roles.iterator().next().getId()
//                : 0;
//    }
}
