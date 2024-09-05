package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dto.CartDto;
import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.model.CartItem;

/**
 * <p>
 * CartItem service represents Crud functions with business logic
 * </p>
 *
 * @author Jeevithakesavaraj
 */
public interface CartItemService {

    /**
     * <p>
     *  Add product to cart item
     * </p>
     * @param cart  {@link Cart}
     * @param cartDto {@link CartDto}
     * @return cartItem {@link CartItem}
     */
    CartItem addProductToCartItem(Cart cart, CartDto cartDto);

    /**
     * <p>
     * Delete the cartItem
     * </p>
     *
     * @param cartItem  {@link CartItem}
     */
    void deleteCartItem(CartItem cartItem);

    /**
     * <p>
     * Update product to cart item
     * </p>
     * @param cartItem {@link CartItem}
     * @param cartDto {@link CartDto}
     * @return cartItem {@link CartItem}
     */
    CartItem updateProductToCartItem(CartItem cartItem, CartDto cartDto);
}
