package com.ideas2it.flipzon.controller;

import com.ideas2it.flipzon.dto.*;
import com.ideas2it.flipzon.service.CartItemService;
import com.ideas2it.flipzon.service.CartService;
import com.ideas2it.flipzon.service.CustomerService;
import com.ideas2it.flipzon.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * CustomerController handles all incoming HTTP requests related to brands and return the responses
 * </p>
 *
 * @author Gowthamraj
 */
@RestController
@RequestMapping("flipzon/api/v1/customers")
public class CustomerController {
    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartService cartService;

    /**
     * <p>
     * Adds a specific product to a customer's wishlist.
     * </p>
     *
     * @param customerId To specify a customer.
     * @param productId To specify a product has to be added.
     * @return wishlistDto
     */
    @PutMapping("/{customerId}/wishlist/{productId}")
    public ResponseEntity<WishlistResponseDto> addProductToWishlist(@PathVariable long customerId, @PathVariable long productId) {
        WishlistResponseDto updatedWishlist = wishlistService.addProductToWishlist(customerId, productId);
        return ResponseEntity.ok(updatedWishlist);
    }

    /**
     * <p>
     * Retrieves all products from a wishlist of a particular customer.
     * </p>
     * @param customerId Specify the customer
     * @return wishlist of a customer
     */
    @GetMapping("/{customerId}/wishlist")
    public ResponseEntity<WishlistResponseDto> getProductsFromWishlist(@PathVariable long customerId) {
        return new ResponseEntity<>(wishlistService.getProductsFromWishlist(customerId), HttpStatus.OK);
    }

    /**
     * <p>
     * Removes product from a customer's wishlist.
     * </p>
     *
     * @param customerId Specifies which customer.
     * @param productId Specifies which product needs to be removed.
     * @return a updated wishlist.
     */
    @DeleteMapping("/{customerId}/wishlist/products/{productId}")
    public ResponseEntity<WishlistResponseDto> removeProductFromWishlist(@PathVariable long customerId, @PathVariable long productId) {
        return new ResponseEntity<>(wishlistService.removeProductFromWishlist(customerId, productId), HttpStatus.OK);
    }

    @PutMapping("/addproducts")
    public ResponseEntity<CartResponseDto> addProductToCart(@RequestBody CartDto cartDto) {
        CartResponseDto updatedCart = cartService.addProductToCart(cartDto);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/{customerId}/cart")
    public ResponseEntity<CartResponseDto> getProductsFromCart(@PathVariable long customerId) {
        return new ResponseEntity<>(cartService.getProductsFromCart(customerId), HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}/cart/{productId}")
    public ResponseEntity<CartResponseDto> removeProductFromCart(@PathVariable long customerId, @PathVariable long productId) {
        return new ResponseEntity<>(cartService.removeProductFromCart(customerId, productId), HttpStatus.OK);
    }

    @PutMapping("/update-quantity")
    public ResponseEntity<CartResponseDto> updateProductQuantity(@RequestBody CartDto cartDto) {
        CartResponseDto updatedCart = cartService.updateProductQuantity(cartDto);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }
}
