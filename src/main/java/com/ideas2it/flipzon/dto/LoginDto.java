package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * LoginDto have login details that is email and password
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Data
@Builder
public class LoginDto {
    private String email;
    private String password;
}
