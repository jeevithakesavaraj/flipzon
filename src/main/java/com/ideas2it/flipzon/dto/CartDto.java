package com.ideas2it.flipzon.dto;

import com.ideas2it.flipzon.model.CartItem;
import lombok.Data;
import lombok.Builder;

import java.util.Set;

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
    private long customerId;
    private long productId;
    private int quantity;
}
