package com.ideas2it.flipzon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.flipzon.model.Delivery;
/**
 * <p>
 * DeliveryDao manages CRUD operations for delivery person details.
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Repository
public interface DeliveryDao extends JpaRepository<Delivery, Long> {
}
