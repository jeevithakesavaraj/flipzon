package com.ideas2it.flipzon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object of Product Entity
 *
 * @author Gokul
 */
@Data
@Builder
public class ProductDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull(message = "Price is mandatory")
    private double price;

    @NotNull(message = "Brand ID is mandatory")
    private Long brandId;

    @NotNull(message = "Category ID is mandatory")
    private Long categoryId;

    @NotNull(message = "Subcategory ID is mandatory")
    private Long subcategoryId;
}
