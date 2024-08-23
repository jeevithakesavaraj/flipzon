package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

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

    private long id;

    private String name;

    private String email;

    private String phoneNumber;
}
