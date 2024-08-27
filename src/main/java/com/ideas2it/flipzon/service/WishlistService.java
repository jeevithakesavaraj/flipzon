package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.model.Wishlist;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.model.Customer;

/**
 * <p>
 * WishlistService represents CRUD operations for user and get user by id
 * </p>
 *
 * @author Gowthamraj
 */
@Service
public interface WishlistService {

    /**
     * <p>
     * Add product to the wishlist
     * </p>
     *
     * @param wishlistId To specify the wishlist
     * @param productId To specify the product.
     */
    void addProductToWishlist(long wishlistId, long productId);

    /**
     * <p>
     * Add wishlist to a customer while signup.
     * </p>
     *
     * @param customer To refer a customer.
     */
    Wishlist addWishlistToCustomer(Customer customer);
}
