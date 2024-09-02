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
     * Gets the Wishlist of a customer.
     * </p>
     * @param customerId Unique identification of a customer.
     * @return Wishlist of a customer {@link Wishlist}
     */
    Wishlist findByCustomerId(long customerId);

    /**
     * <p>
     * Checks if the name is present or not
     * </p>
     * @param customerId Unique identification of a customer.
     * @return boolean if customer exists, return true or false.
     */
    boolean existsByCustomerId(long customerId);
}
