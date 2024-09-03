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
    /**
     * <p>
     * Adds the product to cart.
     * </p>
     *
     * @param cartDto {@link CartDto}
     * @return {@link CartResponseDto}
     */
    CartResponseDto addProductToCart(CartDto cartDto);

    /**
     * <p>
     * Retrieves the products from a cart.
     * </p>
     *
     * @param customerId To specify which customer.
     * @return List of cartItems. {@link CartResponseDto}
     */
    CartResponseDto getProductsFromCart(long customerId);

    /**
     * <p>
     * Removes a product from the cart.
     * </p>
     *
     * @param customerId To specify which customer.
     * @param productId To specify which products needs to be deleted.
     * @return Updated Cart. {@link CartResponseDto}
     */
    CartResponseDto removeProductFromCart(long customerId, long productId);

    /**
     * <p>
     * Specifically updates the quantity of a product in cart.
     * </p>
     *
     * @param cartDto To update the quantity.
     * @return Updated Cart. {@link CartResponseDto}
     */
    CartResponseDto updateProductQuantity(CartDto cartDto);

    /**
     * <p>
     * Retrieves the cart of a customer.
     * </p>
     *
     * @param customerId To specify which customer.
     * @return Cart of a customer. {@link Cart}
     */
    Cart getCartByCustomerId(long customerId);
}
