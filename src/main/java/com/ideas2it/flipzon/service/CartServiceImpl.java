package com.ideas2it.flipzon.service;

import java.util.Set;
import java.util.stream.Collectors;

import com.ideas2it.flipzon.dto.CartResponseDto;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.mapper.CartItemMapper;
import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.model.CartItem;
import com.ideas2it.flipzon.model.Customer;
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
    @Autowired
    private ProductService productService;

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
        cart = cartDao.saveAndFlush(newCart);
        return CartResponseDto.builder()
                .customerId(cart.getCustomer().getId())
                .totalPrice(cart.getTotalPrice())
                .cartItemResponseDtos(cart.getCartItems().stream().map(CartItemMapper::convertEntityToDto).collect(Collectors.toSet()))
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
        existCart = cartDao.saveAndFlush(existCart);
        return CartResponseDto.builder()
                .customerId(existCart.getCustomer().getId())
                .totalPrice(existCart.getTotalPrice())
                .cartItemResponseDtos(existCart.getCartItems().stream().map(CartItemMapper::convertEntityToDto).collect(Collectors.toSet()))
                .build();
    }

    public CartResponseDto getProductsFromCart(long customerId) {
        Cart cart = cartDao.findByCustomerId(customerId);
        return CartResponseDto.builder()
                .customerId(cart.getCustomer().getId())
                .totalPrice(cart.getTotalPrice())
                .cartItemResponseDtos(cart.getCartItems().stream().map(CartItemMapper::convertEntityToDto).collect(Collectors.toSet()))
                .build();
    }

    public CartResponseDto removeProductFromCart(long customerId, long productId) {
        Cart cart = cartDao.findByCustomerId(customerId);
        productService.retrieveProductById(productId);
        for(CartItem cartItems : cart.getCartItems()) {
            if (cartItems.getProduct().getId() == productId) {
                cartItemService.deleteCartItem(cartItems);
                cart.getCartItems().remove(cartItems);
                cart.setTotalPrice(cartItems.getTotalPrice());
                Cart updatedCart = cartDao.saveAndFlush(cart);
                return CartResponseDto.builder()
                        .customerId(updatedCart.getCustomer().getId())
                        .totalPrice(updatedCart.getTotalPrice())
                        .cartItemResponseDtos(updatedCart.getCartItems().stream().map(CartItemMapper::convertEntityToDto).collect(Collectors.toSet()))
                        .build();
            }
        }
        throw new ResourceNotFoundException("customerId" + customerId, "ProductId", productId);
    }

    public CartResponseDto updateProductQuantity(CartDto cartDto) {
        Cart cart = cartDao.findByCustomerId(cartDto.getCustomerId());
        for(CartItem cartItems : cart.getCartItems()) {
            if (cartItems.getProduct().getId() == cartDto.getProductId()) {
                cartItems = cartItemService.updateProductToCartItem(cartItems, cartDto);
                cartItems.setQuantity(cartDto.getQuantity());
                cartItems.setTotalPrice(cartItems.getPrice() * cartDto.getQuantity());
                cart.getCartItems().add(cartItems);
                cart.setTotalPrice(cartItems.getTotalPrice());
                Cart updatedCart = cartDao.saveAndFlush(cart);
                return CartResponseDto.builder()
                        .customerId(updatedCart.getCustomer().getId())
                        .totalPrice(updatedCart.getTotalPrice())
                        .cartItemResponseDtos(updatedCart.getCartItems().stream().map(CartItemMapper::convertEntityToDto).collect(Collectors.toSet()))
                        .build();
            }
        }
        throw new ResourceNotFoundException("customerId" + cartDto.getCustomerId(), "ProductId", cartDto.getProductId());
    }

    public Cart getCartByCustomerId(long customerId) {
        return cartDao.findByCustomerId(customerId);
    }
}