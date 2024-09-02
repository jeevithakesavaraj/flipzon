package com.ideas2it.flipzon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.flipzon.model.Order;

import java.util.List;

/**
 * <p>
 * OrderDao represents CRUD operations for order
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
    /**
     * <p>
     *  Get the list of orders by customer Id
     * </p>
     * @param customerId Id of the customer
     * @return List<Order> {@link Order}
     */
    List<Order> findByCustomerIdAndIsDeletedFalse(long customerId);

    Order findByIdAndCustomerId(long id, long customerId);

}
