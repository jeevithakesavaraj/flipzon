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
     * Add cart
     * </p>
     *
     * @param cartDto {@link CartDto}
     */
    void addCart(CartDto cartDto);

    /**
     * <p>
     * Get cart by Id
     * </p>
     * @param id   Id of the cart
     * @return CartDto {@link CartDto}
     */
    CartDto getCartById(long id);
}
