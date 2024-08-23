package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dao.UserDao;
import com.ideas2it.flipzon.dto.UserDto;
import com.ideas2it.flipzon.mapper.UserMapper;
import com.ideas2it.flipzon.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public UserDto createUser(UserDto userDto) {
        User user = userDao.save(UserMapper.covertDtoToEntity(userDto));
        return UserMapper.convertEntityToDto(user);
    }
}
