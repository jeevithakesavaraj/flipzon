package com.ideas2it.flipzon.service;

import java.util.Set;

import com.ideas2it.flipzon.dto.CartResponseDto;
import com.ideas2it.flipzon.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.CartDao;
import com.ideas2it.flipzon.dto.CartDto;


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
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CartItemService cartItemService;

    public CartResponseDto addProductToCart(CartDto cartDto) {
        Cart cart = cartDao.findByCustomerId(cartDto.getCustomerId());
        if (cart == null) {
            return createCartAndAddProduct(cartDto);
        } else {
            return existCartAddProduct(cart, cartDto);
        }
    }

    private CartResponseDto createCartAndAddProduct(CartDto cartDto) {
        Cart cart = new Cart();
        double totalPrice = 0;
        Customer customer = customerService.getCustomerById(cartDto.getCustomerId());
        cart.setCustomer(customer);
        Cart newCart = cartDao.saveAndFlush(cart);
        CartItem cartItem = cartItemService.addProductToCartItem(newCart, cartDto);
        newCart.setCartItems(Set.of(cartItem));
        for (CartItem item : newCart.getCartItems()) {
            totalPrice += item.getTotalPrice();
        }
        newCart.setTotalPrice(totalPrice);
        cartDao.saveAndFlush(newCart);
        return CartResponseDto.builder()
                .customerId(cartDto.getCustomerId())
                .totalPrice(newCart.getTotalPrice())
                .build();
    }

    private CartResponseDto existCartAddProduct(Cart cart, CartDto cartDto) {
        double totalPrice = 0;
        Cart existCart = cartDao.findByCustomerId(cartDto.getCustomerId());
        CartItem cartItem = cartItemService.addProductToCartItem(existCart, cartDto);
        if (existCart.getCartItems() == null) {
            existCart.setCartItems(Set.of(cartItem));
            existCart.setTotalPrice(cartItem.getTotalPrice());
        } else {
            Set<CartItem> cartItems = existCart.getCartItems();
            cartItems.add(cartItem);
            existCart.setCartItems(cartItems);
            for (CartItem item : cart.getCartItems()) {
                totalPrice += item.getTotalPrice();
            }
            existCart.setTotalPrice(totalPrice);
        }
        cartDao.saveAndFlush(existCart);
        return CartResponseDto.builder()
                .customerId(cartDto.getCustomerId())
                .totalPrice(existCart.getTotalPrice())
                .build();
    }
}