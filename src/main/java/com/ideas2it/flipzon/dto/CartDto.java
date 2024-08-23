package com.ideas2it.flipzon.dto;

import java.util.Set;

import lombok.Data;
import lombok.Builder;

import com.ideas2it.flipzon.model.CartItem;

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
    private long id;
    private long customerId;
    private Set<CartItem> cartItems;
}
