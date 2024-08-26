package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dto.LoginDto;
import com.ideas2it.flipzon.model.*;
import com.ideas2it.flipzon.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Set;

import com.ideas2it.flipzon.mapper.UserMapper;
import com.ideas2it.flipzon.common.APIResponse;
import com.ideas2it.flipzon.dto.UserDto;

@Service
public class LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);

    public APIResponse signUp(UserDto userDto) {
        APIResponse apiResponse = new APIResponse();
        Role role = roleService.getRoleByName(UserRole.CUSTOMER);
        User user = UserMapper.convertDtoToEntity(userDto);
        if (! userService.checkByEmail(userDto.getEmail())) {
            user.setPassword(encoder.encode(user.getPassword()));
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            userService.addUser(user);
            Customer customer = Customer.builder()
                    .user(user).build();
            customerService.createCustomer(customer);
            apiResponse.setData(user);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        User checkUser = userService.getByEmail(userDto.getEmail());
        boolean roleExist = false;
        for (Role checkRole  : checkUser.getRoles()) {
            if (checkRole.getId() == role.getId()) {
                roleExist = true;
                break;
            }
        }
        if(!roleExist) {
            user.setId(checkUser.getId());
            checkUser.getRoles().add(role);
            user.setRoles(checkUser.getRoles());
            userService.addUser(user);
            Customer customer = new Customer();
            customer.setUser(user);
            customerService.createCustomer(customer);
            apiResponse.setData(user);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        apiResponse.setStatus(HttpStatus.FOUND.value());
        return apiResponse;
    }

    public APIResponse deliverySignUp(UserDto userDto) {
        APIResponse apiResponse = new APIResponse();
        Role role = roleService.getRoleByName(UserRole.DELIVERYPARTNER);
        User user = UserMapper.convertDtoToEntity(userDto);
        if (! userService.checkByEmail(userDto.getEmail())) {
            user.setPassword(encoder.encode(user.getPassword()));
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            userService.addUser(user);
            Delivery delivery = Delivery.builder()
                    .user(user)
                    .build();
            deliveryService.createDelivery(delivery);
            apiResponse.setData(user);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        User checkUser = userService.getByEmail(userDto.getEmail());
        boolean roleExist = false;
        for (Role checkRole  : checkUser.getRoles()) {
            if (checkRole.getId() == role.getId()) {
                roleExist = true;
                break;
            }
        }
        if(!roleExist) {
            user.setId(checkUser.getId());
            checkUser.getRoles().add(role);
            user.setRoles(checkUser.getRoles());
            userService.addUser(user);
            Customer customer = new Customer();
            customer.setUser(user);
            customerService.createCustomer(customer);
            apiResponse.setData(user);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        apiResponse.setStatus(HttpStatus.FOUND.value());
        return apiResponse;
    }

    public APIResponse logIn(LoginDto loginDto) {
        APIResponse apiResponse = new APIResponse();
        User user = userService.getByEmail(loginDto.getEmail());
        if (encoder.matches(loginDto.getPassword(), user.getPassword())) {
            String token = jwtService.generateJwt(user);
            apiResponse.setData(token);
            apiResponse.setStatus(HttpStatus.FOUND.value());
            return apiResponse;
        }
        apiResponse.setData("User not found");
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return apiResponse;

    }
}
