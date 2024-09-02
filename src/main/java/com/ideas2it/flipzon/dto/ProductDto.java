package com.ideas2it.flipzon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object of Product Entity
 *
 * @author Gokul
 */
@Getter
@Setter
@Builder
public class ProductDto {

    private Long id;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]+( [a-zA-Z])*$", message = "Name only contains Characters")
    private String name;

    @NotNull(message = "Price is mandatory")
    private double price;

    @NotNull(message = "Brand ID is mandatory")
    private Long brandId;

    @NotNull(message = "Category ID is mandatory")
    private Long categoryId;

    @NotNull(message = "Subcategory ID is mandatory")
    private Long subcategoryId;
}
