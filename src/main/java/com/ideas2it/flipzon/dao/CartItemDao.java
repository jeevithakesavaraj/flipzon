package com.ideas2it.flipzon.dao;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.flipzon.model.CartItem;

/**
 * <p>
 * CartItemDao is for accessing cart item from database
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@SpringBootApplication(scanBasePackages = "com.ideas2it.flipzon")
@Repository
public interface CartItemDao extends JpaRepository<CartItem, Long> {

    /**
     * <p>
     * Gets a cartItem of a cart.
     * </p>
     * @param id  Unique identification of a cart.
     * @return CartItem of a cart. {@link CartItem}
     */
    CartItem findByProductId(long id);

}
