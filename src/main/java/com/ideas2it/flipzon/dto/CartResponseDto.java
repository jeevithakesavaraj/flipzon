package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CartResponseDto {

    private Long  customerId;

    private Set<CartItemResponseDto> cartItemResponseDtos;

    private double totalPrice;

}
