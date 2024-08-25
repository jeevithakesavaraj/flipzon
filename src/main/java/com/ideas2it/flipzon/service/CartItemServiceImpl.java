package com.ideas2it.flipzon.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ideas2it.flipzon.dao.CartItemDao;
import com.ideas2it.flipzon.dto.CartItemDto;
import com.ideas2it.flipzon.mapper.CartItemMapper;
import com.ideas2it.flipzon.model.CartItem;

public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemDao cartItemdao;

    @Override
    public void addCartItem(CartItemDto cartItemDto) {
        CartItem cartItem = CartItemMapper.convertDtoToEntity(cartItemDto);
        cartItemdao.save(cartItem);
    }

}
