package com.ideas2it.flipzon.controller;

import com.ideas2it.flipzon.dto.LoginDto;
import com.ideas2it.flipzon.util.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ideas2it.flipzon.common.APIResponse;
import com.ideas2it.flipzon.dto.UserDto;
import com.ideas2it.flipzon.service.CustomerService;
import com.ideas2it.flipzon.service.LoginService;

@RestController
@RequestMapping("flipzon/api/v1")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/users/customers/signup")
    public ResponseEntity<APIResponse> signUp(@Valid @RequestBody UserDto userDto) {
        APIResponse apiResponse = loginService.signUp(userDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @PostMapping("/users/delivery/signup")
    public ResponseEntity<APIResponse> deliverySignUp(@Valid @RequestBody UserDto userDto) {
        APIResponse apiResponse = loginService.deliverySignUp(userDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @PostMapping("/users/login")
    public ResponseEntity<APIResponse> logIn(@Valid @RequestBody LoginDto loginDto) {
        APIResponse apiResponse = loginService.logIn(loginDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @GetMapping("/private")
    public ResponseEntity<APIResponse> privateAPI(@RequestHeader(value = "authorization") String authorization) {
        APIResponse apiResponse = new APIResponse();
        jwtService.verifyToken(authorization);
        apiResponse.setData("this is private");
        apiResponse.setStatus(HttpStatus.OK.value());
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
