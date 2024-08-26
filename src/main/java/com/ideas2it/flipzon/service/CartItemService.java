package com.ideas2it.flipzon.service;

import java.util.List;

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

    /**
     * <p>
     * Get cart items
     * </p>
     * @return CartItemDtos  {@link CartItemDto}
     */
    List<CartItemDto> getCartItems();

    /**
     * <p>
     * Delete cart item
     * </p>
     * @param cartItemId  Id of the cart item which we have to delete
     */
    void deleteCartItem(long cartItemId);
}
