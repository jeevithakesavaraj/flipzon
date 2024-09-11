package com.ideas2it.flipzon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.flipzon.dto.WishlistResponseDto;
import com.ideas2it.flipzon.helper.JwtHelper;
import com.ideas2it.flipzon.service.CustomerService;
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

    @Autowired
    private CustomerService customerService;

    /**
     * <p>
     * Adds a specific product to a customer's wishlist.
     * </p>
     *
     * @param productId To specify a product has to be added.
     * @return {@link WishlistResponseDto} with Http status OK
     */
    @PutMapping("/me/wishlists/products/{productId}")
    public ResponseEntity<WishlistResponseDto> addProductToWishlist(@PathVariable long productId) {
        long customerId = customerService.getCustomerIdByUserName(JwtHelper.extractUserNameFromToken());
        WishlistResponseDto updatedWishlist = wishlistService.addProductToWishlist(customerId, productId);
        return ResponseEntity.ok(updatedWishlist);
    }

    /**
     * <p>
     * Retrieves all products from a wishlist of a particular customer.
     * </p>
     *
     * @return {@link WishlistResponseDto} with Http status OK
     */
    @GetMapping("/me/wishlists")
    public ResponseEntity<WishlistResponseDto> getProductsFromWishlist() {
        long customerId = customerService.getCustomerIdByUserName(JwtHelper.extractUserNameFromToken());
        return new ResponseEntity<>(wishlistService.getProductsFromWishlist(customerId), HttpStatus.OK);
    }

    /**
     * <p>
     * Removes product from a customer's wishlist.
     * </p>
     *
     * @param productId Specifies which product needs to be removed.
     * @return {@link WishlistResponseDto} with Http status OK
     */
    @DeleteMapping("/me/wishlists/products/{productId}")
    public ResponseEntity<WishlistResponseDto> removeProductFromWishlist(@PathVariable long productId) {
        long customerId = customerService.getCustomerIdByUserName(JwtHelper.extractUserNameFromToken());
        return new ResponseEntity<>(wishlistService.removeProductFromWishlist(customerId, productId), HttpStatus.NO_CONTENT);
    }
}
