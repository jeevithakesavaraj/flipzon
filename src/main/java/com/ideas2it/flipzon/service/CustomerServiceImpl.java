package com.ideas2it.flipzon.service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private CustomerDao customerDao;

    public Customer addCustomer(Customer customer) {
        return customerDao.save(customer);
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerDao.findAll();
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer : customers) {
            customerDtos.add(CustomerMapper.convertEntityToDto(customer));
        }
        return customerDtos;
    }

    public Customer getCustomerById(long id) {
        return customerDao.findById(id).get();
    }

    public boolean isCustomerPresent(long customerId) {
        return customerDao.existsByIdAndIsDeletedFalse(customerId);
    }
}
