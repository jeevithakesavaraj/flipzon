package com.ideas2it.flipzon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * Cross-sell Request Data transfer object
 * @author Gokul
 */
@Data
@Builder
public class CrossSellRequestDto {

    @NotNull
    @NotBlank
    private Long crossSellProductId;
}
