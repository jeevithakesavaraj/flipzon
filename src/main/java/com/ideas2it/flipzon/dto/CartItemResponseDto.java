package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * This represents for token which is response back to the user
 * </p>
 *
 * @author Gowthamraj
 */
@Builder
@Data
public class CartItemResponseDto {
    private String productName;
    private int quantity;
    private double totalPrice;
}
