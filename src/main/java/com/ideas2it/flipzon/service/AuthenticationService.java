package com.ideas2it.flipzon.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.common.APIResponse;
import com.ideas2it.flipzon.configuaration.JwtService;
import com.ideas2it.flipzon.exception.MyException;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.dto.AuthenticationResponse;
import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.dto.DeliveryDto;
import com.ideas2it.flipzon.dto.LoginDto;
import com.ideas2it.flipzon.mapper.UserMapper;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Delivery;
import com.ideas2it.flipzon.model.Role;
import com.ideas2it.flipzon.model.User;
import com.ideas2it.flipzon.model.UserRole;

/**
 * <p>
 * This class represents for service for user authentication
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private APIResponse apiResponse;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final CustomerService customerService;
    private final DeliveryService deliveryService;

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationService.class);

    /**
     * <p>
     * register customer
     * </p>
     * @param customerDto {@link CustomerDto}
     * @return APIresponse {@link APIResponse}
     */
    public APIResponse registerCustomer(CustomerDto customerDto) {
        Role roles = roleService.getRoleByName(UserRole.ROLE_CUSTOMER);
        if (userService.checkByEmail(customerDto.getEmail())) {
            User existingUser = userService.getByEmail(customerDto.getEmail());
            boolean checkRole = checkRole(existingUser.getRole(), roles.getId());
            if (!checkRole) {
                Set<Role> userRoles = existingUser.getRole();
                userRoles.add(roles);
                existingUser.setId(customerDto.getId());
                existingUser.setRole(userRoles);
                User savedUser = userService.addUser(existingUser);
                Customer customer = UserMapper.convertCustomerEntity(customerDto, savedUser);
                customerService.addCustomer(customer);
                apiResponse.setData(savedUser.getEmail() + " registered successfully.");
                apiResponse.setStatus(HttpStatus.CREATED.value());
                return apiResponse;
            }
            throw new MyException("Email Id - " + customerDto.getEmail()
                    + " Already Exist.Please Login or use Another EmailId");
        }
        User user = UserMapper.convertUserEntity(customerDto);
        user.setRole(Collections.singleton(roles));
        User savedUser = userService.addUser(user);
        Customer customer = UserMapper.convertCustomerEntity(customerDto, savedUser);
        customerService.addCustomer(customer);
        apiResponse.setData(savedUser.getEmail() + " registered successfully.");
        apiResponse.setStatus(HttpStatus.CREATED.value());
        return apiResponse;

    }

    /**
     * <p>
     *  Register delivery person
     * </p>
     * @param deliveryDto  {@link DeliveryDto}
     * @return APIResponse {@link APIResponse}
     */
    public APIResponse registerDeliveryPerson(DeliveryDto deliveryDto) {
        Role role = null;
        if (userService.checkByEmail(deliveryDto.getEmail())) {
            role = roleService.getRoleByName(UserRole.ROLE_DELIVERYPERSON);
            User user = userService.getByEmail(deliveryDto.getEmail());
            boolean checkRole = checkRole(user.getRole(), role.getId());
            if (!checkRole) {
                Set<Role> roles = user.getRole();
                roles.add(role);
                user.setId(user.getId());
                user.setRole(roles);
                User savedUser = userService.addUser(user);
                Delivery delivery = UserMapper.convertDeliveryEntity(deliveryDto, savedUser);
                deliveryService.createDelivery(delivery);
                apiResponse.setData(savedUser.getEmail() + " registered successfully.");
                apiResponse.setStatus(HttpStatus.CREATED.value());
                return apiResponse;
            }
            throw new MyException("Email Id - " + deliveryDto.getEmail()
                    + " Already Exist.Please Login or use Another EmailId");
        }
        role = roleService.getRoleByName(UserRole.ROLE_DELIVERYPERSON);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = UserMapper.convertUserEntity(deliveryDto);
        user.setRole(roles);
        User savedUser = userService.addUser(user);
        Delivery delivery = UserMapper.convertDeliveryEntity(deliveryDto, savedUser);
        deliveryService.createDelivery(delivery);
        apiResponse.setData(savedUser.getEmail() + " registered successfully.");
        apiResponse.setStatus(HttpStatus.CREATED.value());
        return apiResponse;
    }

    /**
     * <p>
     * Authenticate login using credentials
     * </p>
     * @param loginDto {@link LoginDto}
     * @return {@link AuthenticationResponse}
     */
    public AuthenticationResponse authenticate(LoginDto loginDto) {
        User user = userService.getByEmail(loginDto.getEmail());
        if (null == user) {
            throw new ResourceNotFoundException("No user is found in this emailId:", loginDto.getEmail());
        }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            loginDto.getPassword()
                    )
            );
            String jwtToken = jwtService.generateToken(user);
            LOGGER.info("User authenticated successfully!");
            return AuthenticationResponse.builder()
                    .status(HttpStatus.OK.value())
                    .token(jwtToken)
                    .build();

    }

    /**
     * <p>
     * Check if the role is present in the set of roles of the user
     * </p>
     * @param roles  Set of roles where user have registered
     * @param roleId ID of the role
     * @return boolean if the role is present, return true or else false
     */
    public boolean checkRole(Set<Role> roles, long roleId) {
        for (Role userRole : roles) {
            if (userRole.getId() == roleId) {
                return true;
            }
        }
        return false;
    }
}
