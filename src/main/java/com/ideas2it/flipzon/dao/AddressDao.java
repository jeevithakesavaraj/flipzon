package com.ideas2it.flipzon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.flipzon.model.Address;

/**
 * <p>
 *  Address Repository is for accessing address details from database
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Repository
public interface AddressDao extends JpaRepository<Address, Long> {

    /**
     * <p>
     *  Get list of addresses by customer Id
     * </p>
     * @param customerId   Id of the customer whose addresses we want
     * @return List<Address>  list of addresses
     */
    List<Address> findByCustomerIdAndIsDeletedFalse(long customerId);

    boolean existsByIdAndCustomerIdAndIsDeletedFalse(long addressId, long customerId);
}
