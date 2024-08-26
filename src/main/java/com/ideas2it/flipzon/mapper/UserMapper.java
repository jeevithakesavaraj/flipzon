package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.UserDto;
import com.ideas2it.flipzon.model.User;

/**
 * <p>
 * UserMapper have methods for conversion of entity and dto
 * </p>
 */
public class UserMapper {

    /**
     * <p>
     *  Convert UserDto to UserEntity
     * </p>
     * @param userDto {@link UserDto}
     * @return User  {@link User}
     */
    public static User convertDtoToEntity (UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .build();
    }
}
