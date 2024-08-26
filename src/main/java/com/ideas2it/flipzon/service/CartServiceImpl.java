package com.ideas2it.flipzon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.CartDao;
import com.ideas2it.flipzon.dto.CartDto;
import com.ideas2it.flipzon.mapper.CartMapper;
import com.ideas2it.flipzon.model.Cart;

/**
 * <p>
 * CartServiceImpl class implements CartService and inherits the methods in Cart service
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Override
    public CartDto addCart(CartDto cartDto) {
        Cart savedCart = cartDao.save(CartMapper.convertDtoToEntity(cartDto));
        return CartMapper.convertEntityToDto(savedCart);
    }

    @Override
    public List<CartDto> getCarts() {
        return cartDao.findAll().stream()
                .map(CartMapper::convertEntityToDto).toList();
    }

    @Override
    public Cart getCartById(long id) {
        return cartDao.findById(id).orElseThrow();
    }


}
