package com.ideas2it.flipzon.service;

import java.util.List;

import com.ideas2it.flipzon.model.Cart;
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

    CartDto addCart(CartDto cartDto);

    List<CartDto> getCarts();

    Cart getCartById(long id);

}
