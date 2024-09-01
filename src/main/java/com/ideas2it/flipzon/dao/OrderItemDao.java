package com.ideas2it.flipzon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.flipzon.model.OrderItem;

/**
 * <p>
 *  OrderItem dao represents Order Item storing order details
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Repository
public interface OrderItemDao extends JpaRepository<OrderItem, Long> {
}

