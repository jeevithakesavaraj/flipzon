package com.ideas2it.flipzon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ideas2it.flipzon.dao.CartItemDao;
import com.ideas2it.flipzon.dto.CartItemDto;
import com.ideas2it.flipzon.mapper.CartItemMapper;
import com.ideas2it.flipzon.model.CartItem;
import com.ideas2it.flipzon.dto.ProductDto;

/**
 * <p>
 * CartItem service has methods for add product to the cartItemDao
 * </p>
 *
 * @author Jeevithakesavaraj
 */
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private ProductService productService;

    @Override
    public void addCartItem(CartItemDto cartItemDto) {
        CartItem cartItem = CartItemMapper.convertDtoToEntity(cartItemDto);
        ProductDto productDto = productService.retrieveProductById(cartItemDto.getProduct_id());
        cartItem.setTotalPrice(cartItem.getQuantity() * productDto.getPrice());
        CartItem savedCartItem = cartItemDao.save(cartItem);
    }

    @Override
    public List<CartItemDto> getCartItems() {
        return cartItemDao.findAll().stream()
                .map(CartItemMapper::convertEntityToDto).toList();
    }

    @Override
    public void deleteCartItem(long cartItemId) {
        cartItemDao.deleteById(cartItemId);
    }

}
