package com.ideas2it.flipzon.dto;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpsellResponseDto {
    private long productId;
    private String productName;
    private double price;
    private Set<ProductDto> productDtos;
}
