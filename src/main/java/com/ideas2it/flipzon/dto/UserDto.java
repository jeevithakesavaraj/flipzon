package com.ideas2it.flipzon.dto;

import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import com.ideas2it.flipzon.model.Address;
import com.ideas2it.flipzon.model.Role;

/**
 * <p>
 * UserDto is a Data Transfer Object that represents a User.
 * It contains fields for transferring user-related data between layers of the application.
 * </p>
 *
 * @author Gowthamraj
 */
@Data
@Builder
public class UserDto {

    @Valid
    private long id;

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotNull
    private String password;

    private String phoneNumber;

    private long role_id;

    private Set<Role> roles;

    private Address address;
}
