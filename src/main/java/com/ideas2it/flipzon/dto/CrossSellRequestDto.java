package com.ideas2it.flipzon.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CrossSellRequestDto {

    @NotNull
    private Long crossSellProductId;
}
