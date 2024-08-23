package com.ideas2it.flipzon.dto;

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

    private String addressLine;

    private String city;

    private String pinCode;
}
