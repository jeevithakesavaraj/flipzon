package com.ideas2it.flipzon.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.flipzon.common.APIResponse;
import com.ideas2it.flipzon.dto.AuthenticationResponse;
import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.dto.DeliveryPersonDto;
import com.ideas2it.flipzon.dto.LoginDto;
import com.ideas2it.flipzon.dto.UserVerifyDto;
import com.ideas2it.flipzon.exception.AuthenticationException;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.service.AuthenticationService;

/**
 * <p>
 * Authentication controller for register and login for the user
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@RestController
@RequestMapping("flipzon/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private APIResponse apiResponse;
    private final AuthenticationService authenticationService;

    /**
     * <p>
     *  Register customers
     * </p>
     * @param customerDto  {@link CustomerDto}
     * @return String - Customer is registered or not
     */
    @PostMapping("/register/customers")
    public ResponseEntity<APIResponse> registerCustomer(
            @Valid @RequestBody CustomerDto customerDto
    ) {
        apiResponse = authenticationService.registerCustomer(customerDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * <p>
     *  Register delivery person
     * </p>
     * @param deliveryPersonDto {@link DeliveryPersonDto}
     * @return String - Delivery person is registered or not
     */
    @PostMapping("/register/deliverypersons")
    public ResponseEntity<APIResponse> registerDeliveryPerson( @Valid
            @RequestBody DeliveryPersonDto deliveryPersonDto
            ) {
        apiResponse = authenticationService.registerDeliveryPerson(deliveryPersonDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * <p>
     *  Login for the user
     * </p>
     * @param loginDto  {@link LoginDto}
     * @return {@link AuthenticationResponse}
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid
            @RequestBody LoginDto loginDto
            ) {
        AuthenticationResponse authenticationResponse = null;
        try {
            authenticationResponse = authenticationService.authenticate(loginDto);
            return ResponseEntity.status(HttpStatus.OK.value()).body(authenticationResponse);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(null);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(null);
        }
    }

    /**
     * <p>
     * Verify User using OTP
     * </p>
     * @param userVerifyDto  {@link UserVerifyDto}
     * @return APIResponse {@link APIResponse}
     */
    @PostMapping("/verifyCustomer")
    public ResponseEntity<APIResponse> verifyCustomer(@Valid @RequestBody UserVerifyDto userVerifyDto) {
        APIResponse apiResponse = authenticationService.verifyCustomer(userVerifyDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * <p>
     * Verify User using OTP
     * </p>
     * @param userVerifyDto  {@link UserVerifyDto}
     * @return APIResponse {@link APIResponse}
     */
    @PostMapping("/verifyDeliveryPerson")
    public ResponseEntity<APIResponse> verifyDeliveryPerson(@Valid @RequestBody UserVerifyDto userVerifyDto) {
        APIResponse apiResponse = authenticationService.verifyDeliveryPerson(userVerifyDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
