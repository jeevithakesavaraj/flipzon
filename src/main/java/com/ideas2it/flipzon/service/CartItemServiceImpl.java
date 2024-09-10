package com.ideas2it.flipzon.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.CartItemDao;
import com.ideas2it.flipzon.dto.CartDto;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.model.CartItem;
import com.ideas2it.flipzon.model.Product;

@Service
public class CartItemServiceImpl implements CartItemService {

    private static final Logger LOGGER = LogManager.getLogger(CartItemServiceImpl.class);
    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemDao cartItemDao;

    public CartItem addProductToCartItem(Cart cart, CartDto cartDto) {
        CartItem item = cartItemDao.findByProductId(cartDto.getProductId());
        Product product = productService.retrieveProductByIdWithStock(cartDto.getProductId());
        if (item == null) {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(cartDto.getQuantity());
            cartItem.setPrice(product.getPrice());
            cartItem.setTotalPrice(cartDto.getQuantity() * product.getPrice());
            LOGGER.info("Product added in cartItem ");
            CartItem savedCartItem = cartItemDao.saveAndFlush(cartItem);
            return savedCartItem;
        } else {
            return updateProductToCartItem(item, cartDto);
        }
    }

    public void deleteCartItem(CartItem cartItem) {
        cartItemDao.delete(cartItem);
        LOGGER.info("CartItem Deleted successfully");
    }

    public CartItem updateProductToCartItem(CartItem cartItem, CartDto cartDto) {
        cartItem.setQuantity(cartDto.getQuantity());
        cartItem.setPrice(cartItem.getProduct().getPrice());
        cartItem.setTotalPrice(cartDto.getQuantity() * cartItem.getProduct().getPrice());
        LOGGER.info("Product updated in cartItem ");
        return cartItemDao.saveAndFlush(cartItem);
    }
}
