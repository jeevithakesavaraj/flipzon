package com.ideas2it.flipzon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ideas2it.flipzon.dto.UserDto;
import com.ideas2it.flipzon.service.UserService;

import java.util.List;

@RestController
@RequestMapping("flipzon/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * <p>
     * Adds a new user into the database.
     *</p>
     *
     * @param userDto The user data transfer object containing user details.
     * @return The added UserDto object.
     */
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    /**
     * <p>
     * Retrieves all active users.
     * </p>
     *
     * @return A list of UserDto objects.
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    /**
     * <p>
     * Retrieves an user by their ID.
     * </p>
     *
     * @param id The ID of the user to retrieve.
     * @return The UserDto object of the retrieved user.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    /**
     * <p>
     * Removes an user by setting their active status to false.
     * </p>
     *
     * @param id The ID of the user to remove.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable long id) {
        userService.removeUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * <p>
     * Updates an existing user's details.
     * </p>
     *
     * @param userDto The user data transfer object containing updated details.
     * @param id The ID of the user to update.
     * @return The updated UserDto object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateuser(@RequestBody UserDto userDto, @PathVariable long id) {
        return new ResponseEntity<>(userService.updateUser(userDto,id), HttpStatus.ACCEPTED);
    }
}
