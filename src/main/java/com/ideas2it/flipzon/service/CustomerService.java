package com.ideas2it.flipzon.service;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.flipzon.dto.CartDto;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.model.CartItem;
import com.ideas2it.flipzon.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.CustomerDao;
import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.mapper.CustomerMapper;
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

    /**
     * <p>
     * Add customer to the customer database
     * </p>
     *
     * @param customer {@link Customer}
     */
    public void addCustomer(Customer customer) {
        customerDao.save(customer);
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerDao.findAll();
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer : customers) {
            customerDtos.add(CustomerMapper.convertEntityToDto(customer));
        }
        return customerDtos;
    }

    /**
     * <p>
     * Get customer by Id
     * </p>
     *
     * @param id Id of the customer
     * @return Customer {@link Customer}
     */
    public Customer getCustomerById(long id) {
        return customerDao.findById(id).get();
    }

}