package com.ideas2it.flipzon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.flipzon.service.ProductService;
import com.ideas2it.flipzon.service.WishlistService;

/**
 * <p>
 * WishlistController handles all incoming HTTP requests related to products and return the responses
 * </p>
 *
 * @author Gowthamraj
 */
@RestController
@RequestMapping("/flipzon/api/v1/customers/")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private ProductService productService;

    /**
     * <p>
     * Create the Product based on the Admin request
     * </p>
     *
     * @param customerId To specify a customer
     * @param productId To specify a product
     */
    @PostMapping("/{customerId}/wishlist/{productId}")
    public void addProductToWishlist(@PathVariable(name = "customerId") long customerId, @PathVariable(name = "productId") long productId) {
        wishlistService.addProductToWishlist(customerId, productId);
    }
}
