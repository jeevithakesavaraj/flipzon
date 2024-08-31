package com.ideas2it.flipzon.controller;

import com.ideas2it.flipzon.common.APIResponse;
import com.ideas2it.flipzon.dto.*;
import com.ideas2it.flipzon.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private AddressService addressService;

    /**
     * <p>
     * Adds a specific product to a customer's wishlist.
     * </p>
     *
     * @param customerId To specify a customer.
     * @param productId To specify a product has to be added.
     * @return wishlistDto
     */
    @PostMapping("/{customerId}/wishlist/{productId}")
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

    @PostMapping("/addproducts")
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

    @PostMapping("/{customerId}/addresses")
    public ResponseEntity<APIResponse> addAddress(@PathVariable long customerId, @RequestBody AddressDto addressDto) {
        APIResponse apiResponse =  addressService.addAddress(customerId, addressDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @GetMapping("/{customerId}/addresses")
    public ResponseEntity<APIResponse> getAddressesByCustomerId(@PathVariable long customerId) {
        APIResponse apiResponse = addressService.getAddressesByCustomerId(customerId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @PutMapping("/{customerId}/addresses")
    public ResponseEntity<APIResponse> updateAddressByCustomerId(@PathVariable long customerId, @RequestBody AddressDto addressDto) {
        APIResponse apiResponse = addressService.updateAddressByCustomerId(customerId, addressDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @DeleteMapping("/{customerId}/addresses/{addressId}")
    public ResponseEntity<APIResponse> deleteAddressByCustomerId(@PathVariable long customerId, @PathVariable long addressId) {
        APIResponse apiResponse = addressService.deleteAddressByCustomerId(customerId, addressId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }
}
