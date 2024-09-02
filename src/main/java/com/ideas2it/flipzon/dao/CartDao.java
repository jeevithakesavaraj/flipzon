package com.ideas2it.flipzon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.flipzon.model.Cart;

/**
 * <p>
 * CartDao is for accessing cart from database
 * </p>
 *
 * @author Gowthamraj
 */
@Repository
public interface CartDao extends JpaRepository<Cart, Long> {

    /**
     * <p>
     * Checks if the customer is active or not
     * </p>
     * @param customerId  Unique identification of a customer.
     * @return boolean  If the customer is active, return true or else false
     */
    boolean existsByCustomerId(long customerId);

    /**
     * <p>
     * Gets a cart of a customer.
     * </p>
     * @param customerId  Unique identification of a customer.
     * @return Cart of a customer. {@link Cart}
     */
    Cart findByCustomerId(long customerId);
}
