package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * CartItemDto represents Cart item details.
 * </p>
 *
 * @author Jeevithakesavaraj
 */

@Data
@Builder
public class CartItemDto {
    private long id;
    private long cartId;
    private long product_id;
}
