package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dao.CartItemDao;
import com.ideas2it.flipzon.dto.CartDto;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.model.CartItem;
import com.ideas2it.flipzon.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private  ProductService productService;

    @Autowired
    private CartItemDao cartItemDao;

    public CartItem addProductToCartItem(Cart cart, CartDto cartDto) {
        Product product = ProductMapper.convertDtoToEntity(productService.retrieveProductById(cartDto.getProductId()));
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartDto.getQuantity());
        cartItem.setPrice(product.getPrice());
        cartItem.setTotalPrice(cartDto.getQuantity() * product.getPrice());
        return cartItemDao.saveAndFlush(cartItem);
    }
}
