package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * <p>
 * CartItemDto represents Cart item details.
 * </p>
 *
 * @author Gowthamraj
 */

@Data
@Builder
public class CartItemDto {
    private long id;
    private long cartId;
    private int quantity;
    private double price;
    private double totalPrice;
}
