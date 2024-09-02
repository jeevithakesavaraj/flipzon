package com.ideas2it.flipzon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object of subcategory Entity
 *
 * @author Gokul
 */
@Builder
@Data
public class SubcategoryDto {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Category ID is mandatory")
    private Long categoryId;

}
