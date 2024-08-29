package com.ideas2it.flipzon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.flipzon.model.Cart;

import java.util.Optional;

/**
 * <p>
 * CartDao is for accessing cart from database
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Repository
public interface CartDao extends JpaRepository<Cart, Long> {
//    Cart findByCustomerId(long id);

    Optional<Cart> findByCustomerId(long customerId);
}
