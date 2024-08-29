package com.ideas2it.flipzon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.flipzon.dto.AuthenticationResponse;
import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.dto.DeliveryDto;
import com.ideas2it.flipzon.dto.LoginDto;
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

    private final AuthenticationService authenticationService;

    /**
     * <p>
     *  Register customers
     * </p>
     * @param customerDto  {@link CustomerDto}
     * @return String - Customer is registered or not
     */
    @PostMapping("/register/customers")
    public ResponseEntity<String> registerCustomer(
            @RequestBody CustomerDto customerDto
    ) {
        return ResponseEntity.ok(authenticationService.registerCustomer(customerDto));
    }

    /**
     * <p>
     *  Register delivery person
     * </p>
     * @param deliveryDto {@link DeliveryDto}
     * @return String - Delivery person is registered or not
     */
    @PostMapping("/register/deliverypersons")
    public ResponseEntity<String> registerDeliveryPerson(
            @RequestBody DeliveryDto deliveryDto
            ) {
        return ResponseEntity.ok(authenticationService.registerDeliveryPerson(deliveryDto));
    }

    /**
     * <p>
     *  Login for the user
     * </p>
     * @param loginDto  {@link LoginDto}
     * @return {@link AuthenticationResponse}
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginDto loginDto
            ) {
        return ResponseEntity.ok(authenticationService.authenticate(loginDto));

    }
}
