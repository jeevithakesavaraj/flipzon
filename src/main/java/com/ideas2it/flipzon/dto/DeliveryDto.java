package com.ideas2it.flipzon.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * DeliveryDto is a Data Transfer Object that represents a Delivery.
 * It contains fields for transferring delivery-related data between layers of the application.
 * </p>
 *
 * @author Gowthamraj
 */
@Data
@Builder
public class DeliveryDto {
    private long id;

    @NotBlank(message = "Please enter Valid name")
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

    @NotNull(message = "ID proof is mandatory")
    @NotBlank(message = "Please enter the ID proof number")
    @Size(min = 16, max = 16, message = "Please enter 16 digit ID number")
    private String idProof;
}
