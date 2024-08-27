package com.ideas2it.flipzon.userAuthentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.flipzon.dto.LoginDto;
import com.ideas2it.flipzon.dto.UserDto;

/**
 * <p>
 * Authentication controller for register and login for the user
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@RestController
@RequestMapping("api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register/{role}")
    public ResponseEntity<AuthenticationResponse> register(
            @PathVariable String role,
            @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(authenticationService.register(userDto,role));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginDto loginDto
            ) {
        return ResponseEntity.ok(authenticationService.authenticate(loginDto));

    }
}
