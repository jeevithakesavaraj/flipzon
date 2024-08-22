package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CustomerDto {

    private long id;

    private UserDto user;

    private Set<AddressDto> addresses;
}
