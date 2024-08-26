package com.ideas2it.flipzon.dao;

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
}
