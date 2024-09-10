package com.ideas2it.flipzon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object of category Entity
 *
 * @author Gokul
 */
@Builder
@Data
public class CategoryDto {
    private Long id;

    @NotNull
    @NotBlank(message = "Please enter a valid category name")
    private String name;
}
