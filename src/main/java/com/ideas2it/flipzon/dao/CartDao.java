package com.ideas2it.flipzon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.flipzon.model.Cart;
/**
 * <p>
 * CartDao is for accessing cart from database
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Repository
public interface CartDao extends JpaRepository<Cart, Long> {

    /**
     * <p>
     *  Get cart by customer ID
     * </p>
     * @param customerId : id of the customer whose cart we want
     * @return Cart {@link Cart}
     */
    Cart findByCustomerId(long customerId);
}
