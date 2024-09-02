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

    Wishlist findByCustomerId(long customerId);
    boolean existsByCustomerId(long customerId);
}
