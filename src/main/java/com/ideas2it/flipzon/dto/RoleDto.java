package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * RoleDto is a Data Transfer Object that represents a Role.
 * It contains fields for transferring role-related data between layers of the application.
 * </p>
 *
 * @author Gowthamraj
 */
@Data
@Builder
public class RoleDto {

    private long id;

    private String name;
}
