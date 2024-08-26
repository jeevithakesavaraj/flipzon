package com.ideas2it.flipzon.dto;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotNull
    private String password;

    private String phoneNumber;

    private Set<AddressDto> addresses = new HashSet<>();
}
