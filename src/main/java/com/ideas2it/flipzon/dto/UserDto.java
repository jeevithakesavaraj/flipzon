package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private long id;

    private String name;

    private String email;

    private String phoneNumber;
}
