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

    public CartResponseDto addProductToCart(Long customerId, CartDto cartDto) {
        Cart cart = cartDao.findByCustomerId(customerId);
        if (cart == null) {
            return createCartAndAddProduct(customerId, cartDto);
        }
        return existCartAddProduct(cart, cartDto);
    }

    private CartResponseDto createCartAndAddProduct(Long customerId, CartDto cartDto) {
        Cart cart = new Cart();
        Customer customer = customerService.getCustomerById(customerId);
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
        CartItem cartItem = cartItemService.addProductToCartItem(cart, cartDto);
        if (cart.getCartItems() == null) {
            cart.setCartItems(Set.of(cartItem));
            cart.setTotalPrice(cartItem.getTotalPrice());
        } else {
            Set<CartItem> cartItems = cart.getCartItems();
            cartItems.add(cartItem);
            cart.setCartItems(cartItems);
            for (CartItem item : cart.getCartItems()) {
                totalPrice += item.getTotalPrice();
            }
            cart.setTotalPrice(totalPrice);
        }
        cart = cartDao.saveAndFlush(cart);
        LOGGER.info("CartItem added in exist cart");
        return CartResponseDto.builder()
                .customerId(cart.getCustomer().getId())
                .totalPrice(cart.getTotalPrice())
                .cartItemResponseDtos(cart.getCartItems().stream().map(CartItemMapper::convertEntityToDto).collect(Collectors.toSet()))
                .build();
    }

    public CartResponseDto getProductsFromCart(Long customerId) {
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
        }
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
        return CartResponseDto.builder()
                .customerId(cart.getCustomer().getId())
                .totalPrice(cart.getTotalPrice())
                .cartItemResponseDtos(cart.getCartItems().stream().map(CartItemMapper::convertEntityToDto).collect(Collectors.toSet()))
                .build();
    }

    public CartResponseDto updateProductQuantity(Long customerId, CartDto cartDto) {
        Cart cart = cartDao.findByCustomerId(customerId);
        if (cart == null) {
            LOGGER.warn("cart not available update this customer's cart : id {}", customerId);
            throw new ResourceNotFoundException();
        } else if (cart.getCartItems().isEmpty()) {
            LOGGER.warn("cart is empty can't update this customer's cart : id {}", customerId);
            throw new EmptyCart("cart is empty");
        }
        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getProduct().getId() == cartDto.getProductId()) {
                cartItem = cartItemService.updateProductToCartItem(cartItem, cartDto);
                cartItem.setQuantity(cartDto.getQuantity());
                cartItem.setTotalPrice(cartItem.getPrice() * cartDto.getQuantity());
                cart.getCartItems().add(cartItem);
                Cart updatedCart = cartDao.findByCustomerId(customerId);
                LOGGER.info("Product Quantity updated in this cart");
                return CartResponseDto.builder()
                        .customerId(updatedCart.getCustomer().getId())
                        .totalPrice(updatedCart.getTotalPrice())
                        .cartItemResponseDtos(updatedCart.getCartItems().stream().map(CartItemMapper::convertEntityToDto).collect(Collectors.toSet()))
                        .build();
            }
        }
        LOGGER.warn("Product not found in this customers cart");
        throw new ResourceNotFoundException("customerId" + customerId, "ProductId", cartDto.getProductId());
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