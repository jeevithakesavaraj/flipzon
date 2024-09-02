package com.ideas2it.flipzon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

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
