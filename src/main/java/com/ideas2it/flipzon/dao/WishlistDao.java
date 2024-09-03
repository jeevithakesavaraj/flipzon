package com.ideas2it.flipzon.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideas2it.flipzon.model.Wishlist;

/**
 * <p>
 * Repository interface for accessing product data from the database.
 * </p>
 *
 * @author Gowthamraj
 */
public interface WishlistDao extends JpaRepository<Wishlist, Long> {

    /**
     * <p>
     * To get the wishlist of the customer.
     * </p>
     *
     * @param customerId To specify which customer
     * @return Wishlist of the customer. {@link Wishlist}
     */
    Wishlist findByCustomerId(long customerId);

    /**
     * <p>
     * Checks if the cart is exists to the customer or not.
     * </p>
     *
     * @param customerId To specify the which customer.
     * @return Boolean True if exists or false.
     */
    boolean existsByCustomerId(long customerId);
}
