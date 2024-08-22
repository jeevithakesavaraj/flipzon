package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto {

    private long id;

    private String name;
}
