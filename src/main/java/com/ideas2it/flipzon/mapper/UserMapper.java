package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.UserDto;
import com.ideas2it.flipzon.model.User;

public class UserMapper {
    public static User convertDtoToEntity (UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .build();
    }
}
