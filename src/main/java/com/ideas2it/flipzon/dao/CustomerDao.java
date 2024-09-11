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
    /**
     * <p>
     * Check if the customer is present or not by customerID and isDeleted false
     * </p>
     * @param customerId  Id of the customer
     * @return boolean If the customer is present, return true or else false
     */
    boolean existsByIdAndIsDeletedFalse(long customerId);

    /**
     * <p>
     *  Get the customer by customerId
     * </p>
     * @param customerId Id of the customer
     * @return Customer {@link Customer}
     */
    Customer findByIdAndIsDeletedFalse(long customerId);

    /**
     * <p>
     *  Get Customer by user Id
     * </p>
     * @param userId - userId of the customer
     * @return Customer {@link Customer}
     */
    Customer findByUserId(long userId);
}
