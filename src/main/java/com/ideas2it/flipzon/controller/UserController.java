package com.ideas2it.flipzon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.flipzon.dto.UserDto;
import com.ideas2it.flipzon.service.UserService;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * <p>
     * Adds a new user into the database.
     *</p>
     *
     * @param userDto The employee data transfer object containing user details.
     * @return The added UserDto object.
     */
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }
}
