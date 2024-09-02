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
     * Get cartItem by productId
     * </p>
     * @param id  Id of the product
     * @return {@link CartItem}
     */
    CartItem findByProductId(long id);

}
