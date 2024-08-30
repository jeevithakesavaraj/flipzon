package com.ideas2it.flipzon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.flipzon.model.Customer;

/**
 * <p>
 * CustomerDao extends JpaRepository and connected to the database
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Repository
public interface CustomerDao extends JpaRepository<Customer, Long> {
    boolean existsByIdAndIsDeletedFalse(long customerId);
}
