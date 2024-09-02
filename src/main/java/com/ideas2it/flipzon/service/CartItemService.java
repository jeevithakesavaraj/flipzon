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

    CartItem addProductToCartItem(Cart cart, CartDto cartDto);

    void deleteCartItem(CartItem cartItem);

    CartItem updateProductToCartItem(CartItem cartItem, CartDto cartDto);
}
