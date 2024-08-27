package com.ideas2it.flipzon.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.common.APIResponse;
import com.ideas2it.flipzon.dto.UserDto;
import com.ideas2it.flipzon.mapper.UserMapper;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Delivery;
import com.ideas2it.flipzon.model.Role;
import com.ideas2it.flipzon.model.User;
import com.ideas2it.flipzon.model.UserRole;
import com.ideas2it.flipzon.configuaration.JwtService;

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

    /**
     * <p>
     * Sign Up for customer
     * </p>
     * @param userDto   {@link UserDto}
     * @return  API response
     */
    public APIResponse signUp(UserDto userDto) {
        APIResponse apiResponse = new APIResponse();
        Role role = roleService.getRoleByName(UserRole.CUSTOMER);
        User user = UserMapper.convertDtoToEntity(userDto);
        user.setPassword(encoder.encode(user.getPassword()));
        if (userService.checkByEmail(userDto.getEmail())) {
            User existingUser = userService.getByEmail(userDto.getEmail());
            boolean checkRole = checkRole(existingUser.getRoles(), role.getId());
            if (!checkRole) {
                Set<Role> roles = existingUser.getRoles();
                roles.add(role);
                user.setId(existingUser.getId());
                user.setRoles(roles);
                userService.addUser(user);
                Customer customer = Customer.builder()
                        .user(user).build();
                customerService.createCustomer(customer);
                apiResponse.setData(user);
                apiResponse.setStatus(HttpStatus.OK.value());
                return apiResponse;
            }
            apiResponse.setStatus(HttpStatus.FOUND.value());
            return apiResponse;
        }
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        userService.addUser(user);
        customerService.createCustomer(Customer.builder()
                        .user(user)
                .build());
        apiResponse.setData(user);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    /**
     * <p>
     * Signup for delivery person
     * </p>
     *
     * @param userDto {@link UserDto}
     * @return  APIResponse
     */
    public APIResponse deliverySignUp(UserDto userDto) {
        APIResponse apiResponse = new APIResponse();
        Role role = roleService.getRoleByName(UserRole.DELIVERYPERSON);
        User user = UserMapper.convertDtoToEntity(userDto);
        user.setPassword(encoder.encode(user.getPassword()));
        if (userService.checkByEmail(userDto.getEmail())) {
            User existingUser = userService.getByEmail(userDto.getEmail());
            boolean checkRole = checkRole(existingUser.getRoles(), role.getId());
            if (!checkRole) {
                Set<Role> roles = existingUser.getRoles();
                roles.add(role);
                user.setId(existingUser.getId());
                user.setRoles(roles);
                userService.addUser(user);
                Delivery delivery = Delivery.builder()
                        .user(existingUser)
                        .build();
                deliveryService.createDelivery(delivery);
                apiResponse.setData(existingUser);
                apiResponse.setStatus(HttpStatus.OK.value());
                return apiResponse;
            }
            apiResponse.setStatus(HttpStatus.FOUND.value());
            return apiResponse;
        }
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        userService.addUser(user);
        deliveryService.createDelivery(Delivery.builder().user(user).build());
        apiResponse.setData(user.getName());
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }


    public boolean checkRole(Set<Role> roles, long roleId) {
        for (Role role : roles) {
            if (role.getId() == roleId) {
                return true;
            }
        }
        return false;
    }
}
