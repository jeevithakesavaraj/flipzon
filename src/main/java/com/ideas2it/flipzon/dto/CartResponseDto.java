package com.ideas2it.flipzon.dto;

import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.model.CartItem;
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
