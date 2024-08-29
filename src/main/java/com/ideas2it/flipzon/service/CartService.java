package com.ideas2it.flipzon.service;

import java.util.List;

import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.model.Customer;
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
     *  Add cart
     * </p>
     * @param cartDto  {@link CartDto}
     * @return CartDto
     */
    CartDto addCart(CartDto cartDto);

    /**
     * <p>
     * Get list of carts
     * </p>
     * @return List of cartDtos {@link CartDto}
     */
    List<CartDto> getCarts();

    /**
     * <p>
     * Get cart by Id
     * </p>
     * @param id   Id of the cart
     * @return Cart {@link Cart}
     */
    Cart getCartById(long id);

    Cart getCartByCustomerId(Customer customer);

    Cart saveCart(Cart cart);

}
