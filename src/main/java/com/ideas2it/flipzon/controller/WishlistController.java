package com.ideas2it.flipzon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ideas2it.flipzon.dto.WishlistResponseDto;
import com.ideas2it.flipzon.service.WishlistService;
/**
 * <p>
 * WishlistController is for controlling the wishlist operations of customer
 * </p>
 *
 * @author Gowthamraj
 */
@RestController
@RequestMapping("flipzon/api/v1/customers")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;

    /**
     * <p>
     * Adds a specific product to a customer's wishlist.
     * </p>
     *
     * @param customerId To specify a customer.
     * @param productId To specify a product has to be added.
     * @return {@link WishlistResponseDto} with Http status OK
     */
    @PutMapping("/{customerId}/wishlist/products/{productId}")
    public ResponseEntity<WishlistResponseDto> addProductToWishlist(@PathVariable long customerId, @PathVariable long productId) {
        WishlistResponseDto updatedWishlist = wishlistService.addProductToWishlist(customerId, productId);
        return ResponseEntity.ok(updatedWishlist);
    }

    /**
     * <p>
     * Retrieves all products from a wishlist of a particular customer.
     * </p>
     * @param customerId Specify the customer
     * @return {@link WishlistResponseDto} with Http status OK
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
     * @return {@link WishlistResponseDto} with Http status OK
     */
    @DeleteMapping("/{customerId}/wishlist/products/{productId}")
    public ResponseEntity<WishlistResponseDto> removeProductFromWishlist(@PathVariable long customerId, @PathVariable long productId) {
        return new ResponseEntity<>(wishlistService.removeProductFromWishlist(customerId, productId), HttpStatus.OK);
    }

}
