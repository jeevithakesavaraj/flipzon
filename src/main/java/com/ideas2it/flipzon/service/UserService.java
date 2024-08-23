package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    /**
     * <p>
     * Adds a new user to the system.
     * </p>
     *
     * @param userDto The employee details.
     * @return The added UserDto object.
     */
    public UserDto createUser(UserDto userDto);

    /**
     * <p>
     * retrieves all active users.
     * </p>
     * @return A list of userDto objects.
     */
    public List<UserDto> getAllUsers();

    /**
     * <p>
     * retrieves user by their Id.
     * </p>
     * @param id The ID of the user to be updated.
     * @return The UserDto object of the retrieved user.
     */
    public UserDto getUserById(long id);

    /**
     * <p>
     * Removes an user by setting their the isDeleted true.
     * </p>
     * @param id The ID of user to be deleted.
     */
    public void removeUser(long id);

    /**
     * <p>
     * Updates the existing user's details.
     * </p>
     * @param userDto The user details to be updated.
     * @param id The ID of user needs to be updated.
     * @return The updated user object.
     */
    public UserDto updateUser(UserDto userDto, long id);
}
