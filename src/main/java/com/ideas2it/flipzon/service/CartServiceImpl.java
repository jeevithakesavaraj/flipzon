package com.ideas2it.flipzon.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ideas2it.flipzon.dao.CartDao;
import com.ideas2it.flipzon.dto.CartDto;
import com.ideas2it.flipzon.mapper.CartMapper;
import com.ideas2it.flipzon.model.Cart;

/**
 * <p>
 * CartServiceImpl class implements CartService and inherits the methods in Cart service
 * </p>
 */
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Override
    public void addCart(CartDto cartDto) {
        Cart cart = CartMapper.convertDtoToEntity(cartDto);
        cartDao.save(cart);
    }

    @Override
    public CartDto getCartById(long id) {
        Cart cart = cartDao.findById(id).orElseThrow();
        return CartMapper.convertEntityToDto(cart);
    }
}
