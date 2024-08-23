package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.UserDto;
import com.ideas2it.flipzon.model.User;

/**
 * <p>
 * UserMapper is responsible for converting between User entities and UserDto objects.
 * It helps in mapping entity objects to DTOs and vice versa.
 * </p>
 *
 * @author Gowthamraj
 */
public class UserMapper {
    /**
     * <p>
     * Maps User entity to an UserDto.
     * </p>
     *
     * @param user The Employee entity to map.
     * @return The mapped EmployeeDto object.
     */
    public static UserDto convertEntityToDto(User user) {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .roles(Set<Role> roles);
    }
}

