package com.ideas2it.flipzon.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.ideas2it.flipzon.exception.MyException;
import com.ideas2it.flipzon.dto.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.configuaration.JwtService;
import com.ideas2it.flipzon.dto.LoginDto;
import com.ideas2it.flipzon.dto.UserDto;
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
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final CustomerService customerService;
    private final DeliveryService deliveryService;

    public AuthenticationResponse registerCustomer(UserDto userDto) {
        Role roles = roleService.getRoleByName(UserRole.ROLE_CUSTOMER);
        if (userService.checkByEmail(userDto.getEmail())) {
            User existingUser = userService.getByEmail(userDto.getEmail());
            boolean checkRole = checkRole(existingUser.getRole(), roles.getId());
            if (!checkRole) {
                Set<Role> userRoles = existingUser.getRole();
                userRoles.add(roles);
                existingUser.setId(userDto.getId());
                existingUser.setRole(userRoles);
                userService.addUser(existingUser);
                customerService.addCustomer(existingUser);
                String jwtToken = jwtService.generateToken(existingUser);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
            }
            throw new MyException("Email Id - " + userDto.getEmail()
                    + " Already Exist.Please Login or use Another EmailId");
        }
//        Set<Role> roles = new HashSet<>();
//        roles.add(role);
        User savedUser = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .phoneNumber(userDto.getPhoneNumber())
                .build();
        savedUser.setRole(Collections.singleton(roles));
        User saved = userService.addUser(savedUser);
        customerService.addCustomer(savedUser);
        String jwtToken = jwtService.generateToken(saved);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse registerDeliveryPerson(UserDto userDto) {
        Role role = null;
        if (userService.checkByEmail(userDto.getEmail())) {
            role = roleService.getRoleByName(UserRole.ROLE_DELIVERYPERSON);
            User user = userService.getByEmail(userDto.getEmail());
            boolean checkRole = checkRole(user.getRole(), role.getId());
            if (!checkRole) {
                Set<Role> roles = user.getRole();
                roles.add(role);
                user.setId(user.getId());
                user.setRole(roles);
                User savedUser = userService.addUser(user);
                deliveryService.createDelivery(user);
                String jwtToken = jwtService.generateToken(savedUser);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
            }
            throw new MyException("Email Id - " + userDto.getEmail()
                    + " Already Exist.Please Login or use Another EmailId");
        }
        role = roleService.getRoleByName(UserRole.ROLE_DELIVERYPERSON);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .phoneNumber(userDto.getPhoneNumber())
                .role(roles)
                .build();
        userService.addUser(user);
        deliveryService.createDelivery(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse authenticate(LoginDto loginDto) {
        /*authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );*/
        User user = userService.getByEmail(loginDto.getEmail());
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public boolean checkRole(Set<Role> roles, long roleId) {
        for (Role userRole : roles) {
            if (userRole.getId() == roleId) {
                return true;
            }
        }
        return false;
    }
}
