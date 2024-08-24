package com.ideas2it.flipzon.service;

import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dto.CartItemDto;

/**
 * <p>
 * CartItem service represents Crud functions with business logic
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Service
public interface CartItemService {

    /**
     * <p>
     * Add cartItem
     * </p>
     * @param cartItemDto {@link CartItemDto}
     */
    void addCartItem(CartItemDto cartItemDto);
}
