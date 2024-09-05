package com.ideas2it.flipzon.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.CartDao;
import com.ideas2it.flipzon.dto.CartResponseDto;
import com.ideas2it.flipzon.dto.CartDto;
import com.ideas2it.flipzon.exception.EmptyCart;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.mapper.CartItemMapper;
import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.model.CartItem;
import com.ideas2it.flipzon.model.Customer;

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

    private static final Logger LOGGER = LogManager.getLogger(CartServiceImpl.class);

    public CartResponseDto addProductToCart(CartDto cartDto) {
        Cart cart = cartDao.findByCustomerId(cartDto.getCustomerId());
        if (cart == null) {
            return createCartAndAddProduct(cartDto);
        }
        return existCartAddProduct(cart, cartDto);
    }

    private CartResponseDto createCartAndAddProduct(CartDto cartDto) {
        Cart cart = new Cart();
        Customer customer = customerService.getCustomerById(cartDto.getCustomerId());
        cart.setCustomer(customer);
        Cart newCart = cartDao.saveAndFlush(cart);
        CartItem cartItem = cartItemService.addProductToCartItem(newCart, cartDto);
        newCart = cartDao.findByCustomerId(customer.getId());
        newCart.setTotalPrice(cartItem.getTotalPrice());
        cartDao.saveAndFlush(newCart);
        newCart.setCartItems(Set.of(cartItem));
        LOGGER.info("CartItem added in new cart");
        return CartResponseDto.builder()
                .customerId(newCart.getCustomer().getId())
                .totalPrice(newCart.getTotalPrice())
                .cartItemResponseDtos(newCart.getCartItems().stream().map(CartItemMapper::convertEntityToDto).collect(Collectors.toSet()))
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
        LOGGER.info("CartItem added in exist cart");
        return CartResponseDto.builder()
                .customerId(existCart.getCustomer().getId())
                .totalPrice(existCart.getTotalPrice())
                .cartItemResponseDtos(existCart.getCartItems().stream().map(CartItemMapper::convertEntityToDto).collect(Collectors.toSet()))
                .build();
    }

    public CartResponseDto getProductsFromCart(long customerId) {
        Cart cart = cartDao.findByCustomerId(customerId);
        if (cart == null) {
            LOGGER.warn("Cart is empty in this customer id : {}", customerId);
            throw new EmptyCart("cart is empty in this customer id : " + customerId);
        }
        LOGGER.info("Get cart details by customer id : {}", customerId);
        return CartResponseDto.builder()
                .customerId(cart.getCustomer().getId())
                .totalPrice(cart.getTotalPrice())
                .cartItemResponseDtos(cart.getCartItems().stream().map(CartItemMapper::convertEntityToDto).collect(Collectors.toSet()))
                .build();
    }

    public CartResponseDto removeProductFromCart(long customerId, long productId) {
        Cart cart = cartDao.findByCustomerId(customerId);
        if (cart == null) {
            LOGGER.warn("cart not available");
            throw new ResourceNotFoundException();
        } else if (cart.getCartItems().isEmpty()) {
            LOGGER.warn("cart is empty can't remove this customer's cart : id {}", customerId);
            throw new EmptyCart("cart is Empty");
        }
        productService.retrieveProductById(productId);
        LOGGER.info("Remove product from cart by product ID : {}", productId);
        boolean flag = true;
        for (CartItem cartItems : cart.getCartItems()) {
            if (cartItems.getProduct().getId() == productId) {
                cart.getCartItems().remove(cartItems);
                cartItemService.deleteCartItem(cartItems);
                cart.setTotalPrice(cart.getTotalPrice() - cartItems.getTotalPrice());
                cart = cartDao.saveAndFlush(cart);
                flag = false;
            }
        }
        if (flag) {
            LOGGER.warn("product not available in this cart can't remove {}", productId);
            throw new ResourceNotFoundException("productId", "this cart");
        }
        return CartResponseDto.builder()
                .customerId(cart.getCustomer().getId())
                .totalPrice(cart.getTotalPrice())
                .cartItemResponseDtos(cart.getCartItems().stream().map(CartItemMapper::convertEntityToDto).collect(Collectors.toSet()))
                .build();
    }

    public CartResponseDto updateProductQuantity(CartDto cartDto) {
        Cart cart = cartDao.findByCustomerId(cartDto.getCustomerId());
        if (cart == null) {
            LOGGER.warn("cart not available update this customer's cart : id {}", cartDto.getCustomerId());
            throw new ResourceNotFoundException();
        } else if (cart.getCartItems().isEmpty()) {
            LOGGER.warn("cart is empty can't update this customer's cart : id {}", cartDto.getCustomerId());
            throw new EmptyCart("cart is Empty");
        }
        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getProduct().getId() == cartDto.getProductId()) {
                cartItem = cartItemService.updateProductToCartItem(cartItem, cartDto);
                cartItem.setQuantity(cartDto.getQuantity());
                cartItem.setTotalPrice(cartItem.getPrice() * cartDto.getQuantity());
                cart.getCartItems().add(cartItem);
                Cart updatedCart = cartDao.findByCustomerId(cartDto.getCustomerId());
                LOGGER.info("Product Quantity updated in this cart");
                return CartResponseDto.builder()
                        .customerId(updatedCart.getCustomer().getId())
                        .totalPrice(updatedCart.getTotalPrice())
                        .cartItemResponseDtos(updatedCart.getCartItems().stream().map(CartItemMapper::convertEntityToDto).collect(Collectors.toSet()))
                        .build();
            }
        }
        LOGGER.warn("Product not found in this customers cart");
        throw new ResourceNotFoundException("customerId" + cartDto.getCustomerId(), "ProductId", cartDto.getProductId());
    }

    public Cart getCartByCustomerId(long customerId) {
        Cart cart = cartDao.findByCustomerId(customerId);
        if (cart.getCartItems().isEmpty()) {
            LOGGER.warn("Cart is Empty");
            throw new EmptyCart("Cart is Empty");
        }
        LOGGER.info("Get customer cart by is Id : {}", customerId);
        return cart;
    }
}