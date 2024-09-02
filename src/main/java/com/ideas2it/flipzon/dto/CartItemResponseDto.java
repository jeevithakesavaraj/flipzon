package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CartItemResponseDto {
    private String productName;
    private int quantity;
    private double totalPrice;
}
