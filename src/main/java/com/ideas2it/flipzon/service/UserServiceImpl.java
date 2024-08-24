package com.ideas2it.flipzon.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ideas2it.flipzon.dto.RoleDto;
import com.ideas2it.flipzon.mapper.RoleMapper;
import com.ideas2it.flipzon.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.UserDao;
import com.ideas2it.flipzon.dto.UserDto;
import com.ideas2it.flipzon.mapper.UserMapper;
import com.ideas2it.flipzon.model.User;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserDao userDao;

    public UserDto createUser(UserDto userDto) {
        RoleDto roleDto = roleService.getRoleById(userDto.getRole_id());
        userDto.getRoles().add(RoleMapper.convertDtoToEntity(roleDto));
        User user = userDao.save(UserMapper.covertDtoToEntity(userDto));
        return UserMapper.convertEntityToDto(user);
    }

    public List<UserDto> getAllUsers() {
        return userDao.findByIsDeletedFalse().stream()
                .map(UserMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(long id) {
       return UserMapper.convertEntityToDto(userDao.findByIdAndIsDeletedFalse(id));
    }

    public void removeUser(long id) {
        User user = UserMapper.covertDtoToEntity(getUserById(id));
        user.setDeleted(true);
        userDao.save(user);
    }

    public UserDto updateUser(UserDto userDto, long id) {
        User user = UserMapper.covertDtoToEntity(getUserById(id));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setDeleted(false);
        return UserMapper.convertEntityToDto(userDao.save(user));
    }
}
