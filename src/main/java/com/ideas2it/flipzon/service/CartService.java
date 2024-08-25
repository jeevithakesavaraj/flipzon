package com.ideas2it.flipzon.service;

import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dto.CartDto;

/**
 * <p>
 * CartService represents methods declaration for crud operations
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Service
public interface CartService {

    /**
     * <p>
     * Add product to the cart
     * </p>
     * @param cartId   Id of the cart where we have to add the product
     * @param productId   Id of the product which have to add
     * @param quantity    quantity of the product how much we want
     * @return  CartDto {@link CartDto}
     */
    CartDto addProductToCart(Long cartId, Long productId, Integer quantity);

}
