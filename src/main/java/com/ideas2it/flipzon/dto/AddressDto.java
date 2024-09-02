package com.ideas2it.flipzon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * AddressDto is a Data Transfer Object that represents a Address.
 * It contains fields for transferring address-related data between layers of the application.
 * </p>
 *
 * @author Gowthamraj
 */
@Data
@Builder
public class AddressDto {

    private long id;

    @NotBlank(message = "Please enter the address")
    private String addressLine;

    @NotBlank(message = "Please enter your city")
    @Pattern(regexp = "^[a-zA-Z]+( [a-zA-Z])*$", message = "Name only contains Characters")
    private String city;

    @NotBlank(message = "Enter your pincode")
    @Pattern(regexp = "^\\d{6}$", message = "PinCode is 10 digits")
    private String pinCode;
}
