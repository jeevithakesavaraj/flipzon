package com.ideas2it.flipzon.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LogManager.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerDao customerDao;

    public Customer addCustomer(Customer customer) {
        logger.info("customer is added");
        return customerDao.save(customer);
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerDao.findAll();
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer : customers) {
            customerDtos.add(CustomerMapper.convertEntityToDto(customer));
        }
        logger.info("Getting all customer details.");
        return customerDtos;
    }

    public Customer getCustomerById(long customerId) {
        logger.info("Getting the customer with this id: {}", customerId);
        return customerDao.findByIdAndIsDeletedFalse(customerId);
    }

    public boolean isCustomerPresent(long customerId) {
        logger.info("Checking for the customer with this id: {}", customerId);
        return customerDao.existsByIdAndIsDeletedFalse(customerId);
    }
}
