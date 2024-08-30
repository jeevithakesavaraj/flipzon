package com.ideas2it.flipzon.service;

import java.util.List;

import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.model.Customer;

/**
 * <p>
 *  Customer service is for accessing customer details
 * </p>
 *
 * @author Jeevithakesavaraj
 */
public interface CustomerService {

    /**
     * <p>
     * Add customer to the database
     * </p>
     * @param customer {@link Customer}
     * @return savedCustomer {@link Customer}
     */
    Customer addCustomer(Customer customer);

    /**
     * <p>
     * Get all customers
     * </p>
     * @return List of customer {@link CustomerDto}
     */
    List<CustomerDto> getAllCustomers();

    /**
     * <p>
     * Get customer by Id
     * </p>
     * @param id  Id of the customer
     * @return Customer {@link Customer}
     */
    Customer getCustomerById(long id);

    /**
     * <p>
     * Check if the customer is present or not
     * </p>
     * @param customerId Id of the customer
     * @return boolean If customer is present, return true or else false
     */
    boolean isCustomerPresent(long customerId);
}
