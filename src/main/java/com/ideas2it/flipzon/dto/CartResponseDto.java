package com.ideas2it.flipzon.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CartResponseDto {

    private Long  customerId;

    private Set<CartItemResponseDto> cartItemResponseDtos;

    private double totalPrice;

}
