package com.ideas2it.flipzon.userAuthentication;

import java.util.HashSet;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.configuaration.JwtService;
import com.ideas2it.flipzon.dto.LoginDto;
import com.ideas2it.flipzon.dto.UserDto;
import com.ideas2it.flipzon.exception.AccessDeniedException;
import com.ideas2it.flipzon.model.Role;
import com.ideas2it.flipzon.model.User;
import com.ideas2it.flipzon.model.UserRole;
import com.ideas2it.flipzon.service.RoleService;
import com.ideas2it.flipzon.service.UserService;

/**
 * <p>
 *  This class represents for service for user authentication
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

    public AuthenticationResponse register(UserDto userDto, String role) {
        String email = userDto.getEmail();
        for (User user : userService.getAllUsers()){
            if(email.equals(user.getEmail())){
                throw new AccessDeniedException("Email Id - "+ email
                        + " Already Exist.Please Login or use Another EmailId");
            }
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName(UserRole.CUSTOMER));
        var user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .phoneNumber(userDto.getPhoneNumber())
                .roles(roles)
                .build();
        userService.addUser(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        var user = userService.getByEmail(loginDto.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
