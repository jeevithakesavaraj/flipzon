package com.ideas2it.flipzon.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
/**
 * <p>
 * CustomerDto is a Data Transfer Object that represents a Customer.
 * It contains fields for transferring customer-related data between layers of the application.
 * </p>
 *
 * @author Gowthamraj
 */
@Data
@Builder
public class CustomerDto {

    private long id;

    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Please enter Valid name")
    @Pattern(regexp = "^[a-zA-Z]+( [a-zA-Z])*$", message = "Name only contains Characters")
    private String name;

    @NotNull(message = "Email is mandatory")
    @NotBlank(message = "Please enter Valid Email")
    @Email
    private String email;

    @NotNull(message = "Password is mandatory")
    @NotBlank(message = "Password is minimum 8 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "Please enter password contains atleast 1 Capital letter, 1 small letter, 1 special character, 1 number")
    private String password;

    @NotNull(message = "Phone number is mandatory")
    @NotBlank(message = "Please enter the phone number")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number is 10 digits")
    private String phoneNumber;
}
