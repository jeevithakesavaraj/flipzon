package com.ideas2it.flipzon.userAuthentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/register/customers")
    public ResponseEntity<AuthenticationResponse> registerCustomer(
            @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(authenticationService.registerCustomer(userDto));
    }

    @PostMapping("/register/deliverypersons")
    public ResponseEntity<AuthenticationResponse> registerDeliveryPerson(
            @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(authenticationService.registerDeliveryPerson(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginDto loginDto
            ) {
        return ResponseEntity.ok(authenticationService.authenticate(loginDto));

    }
}
