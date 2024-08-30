package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dto.WishlistResponseDto;
import org.springframework.stereotype.Service;

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
     * @param productId  To specify the product.
     */
    WishlistResponseDto addProductToWishlist(long wishlistId, long productId);

    /**
     * <p>
     * Retrieve all products from wishlist
     * </p>
     * @param customerId To specify which customer.
     * @return A list of products
     */
    WishlistResponseDto getProductsFromWishlist(long customerId);

    /**
     * <p>
     * Removes product from wishlist
     * </p>
     *
     * @param customerId To specify which customer.
     * @param productId  To specify which product.
     * @return Updated wishlist. {@link WishlistResponseDto}
     */
    WishlistResponseDto removeProductFromWishlist(long customerId, long productId);

}
