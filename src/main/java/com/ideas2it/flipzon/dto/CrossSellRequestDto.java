package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CrossSellRequestDto {
    private Long productId;
    private Long crossSellProductId;
}
