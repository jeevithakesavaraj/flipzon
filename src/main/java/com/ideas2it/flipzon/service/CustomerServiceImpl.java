package com.ideas2it.flipzon.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.CustomerDao;
import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
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

    public CustomerDto addCustomer(Customer customer) {
        CustomerDto savedCustomerDto = CustomerMapper.convertEntityToDto(customerDao.save(customer));
        logger.info("{}customer is added", customer.getUser().getName());
        return savedCustomerDto;
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
        Customer customer = customerDao.findByIdAndIsDeletedFalse(customerId);
        if (null ==customer) {
            logger.warn("Customer is not found in this Id: {}", customerId);
            throw new ResourceNotFoundException("Customer", customerId);
        }
        logger.info("Getting the customer with this id: {}", customerId);
        return customer;
    }

    public boolean isCustomerPresent(long customerId) {
        logger.info("Checking for the customer with this id: {}", customerId);
        return customerDao.existsByIdAndIsDeletedFalse(customerId);
    }
}
