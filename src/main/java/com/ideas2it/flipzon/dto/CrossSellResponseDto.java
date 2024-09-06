package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CrossSellResponseDto {
    private String ProductName;
    private double price;
    private List<ProductDto> productDtos;
}
