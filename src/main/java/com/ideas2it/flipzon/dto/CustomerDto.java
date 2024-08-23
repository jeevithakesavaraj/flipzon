package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

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

    private UserDto user;

    private Set<AddressDto> addresses;
}
