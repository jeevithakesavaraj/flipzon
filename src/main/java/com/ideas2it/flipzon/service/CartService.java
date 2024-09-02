package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dto.CartResponseDto;
import com.ideas2it.flipzon.model.Cart;

import com.ideas2it.flipzon.dto.CartDto;

/**
 * <p>
 * CartService represents methods declaration for crud operations
 * </p>
 *
 * @author Jeevithakesavaraj
 */
public interface CartService {

    CartResponseDto addProductToCart(CartDto cartDto);

    CartResponseDto getProductsFromCart(long customerId);

    CartResponseDto removeProductFromCart(long customerId, long productId);

    CartResponseDto updateProductQuantity(CartDto cartDto);

    Cart getCartByCustomerId(long customerId);
}
