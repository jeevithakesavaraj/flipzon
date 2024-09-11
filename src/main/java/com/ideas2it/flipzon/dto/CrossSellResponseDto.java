package com.ideas2it.flipzon.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Cross-sell Response Data transfer object
 * @author Gokul
 */
@Builder
@Data
public class CrossSellResponseDto {
    private Long id;
    private String ProductName;
    private double price;
    private List<ProductDto> productDtos;
}
