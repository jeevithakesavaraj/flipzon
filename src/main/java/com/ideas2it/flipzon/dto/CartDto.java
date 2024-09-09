package com.ideas2it.flipzon.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Builder;

/**
 * <p>
 *  CartDto represents cart details
 * </p>
 *
 * @author Jeevithakesavaraj
 */

@Builder
@Data
public class CartDto {

    private Long cartId;

    @NotNull(message = "Product ID is mandatory")
    private long productId;

    @NotNull(message = "Quantity is mandatory")
    private int quantity;
}
