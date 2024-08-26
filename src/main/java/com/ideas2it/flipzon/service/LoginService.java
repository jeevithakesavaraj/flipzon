package com.ideas2it.flipzon.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.mapper.UserMapper;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Role;
import com.ideas2it.flipzon.model.User;
import com.ideas2it.flipzon.model.UserRole;

@Service
public class LoginService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Transactional
    public void customerSignUp(CustomerDto customerDto) {
//        if (userService.checkByEmail(customerDto.getEmail())) {
//
//        }
        User user = UserMapper.convertDtoToUserEntity(customerDto);
        user.setPassword(customerDto.getPassword());
        Customer customer = UserMapper.convertDtoToCustomerEntity(customerDto);
        Role role = roleService.getRoleByName(UserRole.CUSTOMER);
        customer.setUser(user);
        if (user.getRoles() == null)
        {
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
        } else {
            user.getRoles().add(role);
        }
        userService.createUser(user);
        customerService.createCustomer(customer);
    }

    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();

    }
}
