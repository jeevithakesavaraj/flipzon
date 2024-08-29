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


    CartItemDto addProductToCartItem(long customerId, long productId, int quantity);

}
