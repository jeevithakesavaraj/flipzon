package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {

    private long id;

    private String addressLine;

    private String city;

    private String pinCode;
}
