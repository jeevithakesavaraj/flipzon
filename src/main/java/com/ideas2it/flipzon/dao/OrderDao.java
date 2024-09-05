package com.ideas2it.flipzon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.flipzon.model.Order;
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

    /**
     * <p>
     * Get order by order Id and cutomer Id
     * </p>
     * @param id   Id of the order
     * @param customerId Id of the customer
     * @return {@link Order}
     */
    Order findByIdAndCustomerId(long id, long customerId);

}
