package com.ideas2it.flipzon.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object of Product Entity
 * @author Gokul
 */
@Data
@Builder
public class BrandDto {

    private Long id;

    @NotBlank(message = "Brand name is mandatory")
    private String name;
}
