package com.ideas2it.flipzon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.flipzon.model.Payment;

/**
 * <p>
 * It stores payment details with order details
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Repository
public interface PaymentDao extends JpaRepository<Payment, Long> {
}
