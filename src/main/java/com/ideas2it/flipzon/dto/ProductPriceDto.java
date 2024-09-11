package com.ideas2it.flipzon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Product price Data transfer Object
 */
@Builder
@Getter
@Setter
public class ProductPriceDto {

    @NotNull
    @NotBlank
    private double price;
}
