package com.ideas2it.flipzon.service;

import java.util.List;

import com.ideas2it.flipzon.dto.CartDto;
import com.ideas2it.flipzon.dto.CartResponseDto;
import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.model.CartItem;
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

    CartItem addProductToCartItem(Cart cart, CartDto cartDto);

    void deleteCartItem(CartItem cartItem);

    CartItem updateProductToCartItem(CartItem cartItem, CartDto cartDto);
}
