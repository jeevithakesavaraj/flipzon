package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.model.User;
import com.ideas2it.flipzon.model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.CustomerDao;
import com.ideas2it.flipzon.model.Customer;

/**
 * <p>
 * This service is for performing CRUD operations for customer
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private WishlistService wishlistService;

    /**
     * <p>
     * Add customer to the customer database
     * </p>
     *
     * @param user {@link User}
     */
    public void addCustomer(User user) {
        Customer customer = Customer.builder()
                .user(user)
                .build();
         customerDao.save(customer);
    }

    /**
     * <p>
     * Get customer by Id
     * </p>
     *
     * @param id   Id of the customer
     * @return Customer {@link Customer}
     */
    public Customer getCustomerById(long id) {
        return customerDao.findById(id).get();
    }

}
