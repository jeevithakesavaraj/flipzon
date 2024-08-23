package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public UserDto createUser(UserDto userDto);
}
