package com.ideas2it.flipzon.controller;

import com.ideas2it.flipzon.model.Customer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("flipzon/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * <p>
     * Register customers
     * </p>
     *
     * @param customerDto {@link CustomerDto}
     * @return String - Otp verification
     */
    @PostMapping("/customers/register")
    public ResponseEntity<String> registerCustomer(
            @Valid @RequestBody CustomerDto customerDto
    ) {
        String statement = authenticationService.registerCustomer(customerDto);
        return new ResponseEntity<>(statement, HttpStatus.OK);
    }

    /**
     * <p>
     * Register delivery person
     * </p>
     *
     * @param deliveryPersonDto {@link DeliveryPersonDto}
     * @return String - OTP verification
     */
    @PostMapping("/deliverypersons/register")
    public ResponseEntity<String> registerDeliveryPerson(@Valid
                                                              @RequestBody DeliveryPersonDto deliveryPersonDto
    ) {
        String statement = authenticationService.registerDeliveryPerson(deliveryPersonDto);
        return new ResponseEntity<>(statement, HttpStatus.OK);
    }

    /**
     * <p>
     * Login for the user
     * </p>
     *
     * @param loginDto {@link LoginDto}
     * @return {@link AuthenticationResponse}
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginDto loginDto) {
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
     *
     * @param userVerifyDto {@link UserVerifyDto}
     * @return savedCustomer {@link CustomerDto}
     */
    @PostMapping("/customers/verify")
    public ResponseEntity<CustomerDto> verifyCustomer(@Valid @RequestBody UserVerifyDto userVerifyDto) {
        CustomerDto savedCustomerDto = authenticationService.verifyCustomer(userVerifyDto);
        return new ResponseEntity<>(savedCustomerDto, HttpStatus.CREATED);
    }

    /**
     * <p>
     * Verify User using OTP
     * </p>
     *
     * @param userVerifyDto {@link UserVerifyDto}
     * @return savedDeliveryPerson {@link DeliveryPersonDto}
     */
    @PostMapping("/deliverypersons/verify")
    public ResponseEntity<DeliveryPersonDto> verifyDeliveryPerson(@Valid @RequestBody UserVerifyDto userVerifyDto) {
        DeliveryPersonDto deliveryPersonDto = authenticationService.verifyDeliveryPerson(userVerifyDto);
        return new ResponseEntity<>(deliveryPersonDto, HttpStatus.CREATED);
    }
}
