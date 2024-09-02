package com.ideas2it.flipzon.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @NotNull(message = "Email is mandatory")
    @NotBlank(message = "Please enter Valid Email")
    @Email
    private String email;

    @NotNull(message = "Password is mandatory")
    @NotBlank(message = "Password is minimum 8 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "Please enter password contains atleast 1 Capital letter, 1 small letter, 1 special character, 1 number")
    private String password;
}
