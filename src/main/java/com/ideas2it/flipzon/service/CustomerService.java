package com.ideas2it.flipzon.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.CustomerDao;
import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.mapper.UserMapper;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.User;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public void createCustomer(Customer customer) {
        customerDao.save(customer);
    }

    public List<CustomerDto> getAllCustomers() {
        List<CustomerDto> customers = new ArrayList<>();
        for (Customer customer : customerDao.findByIsDeletedFalse()) {
            customers.add(UserMapper.convertEntityToDto(customer));
        }
        return customers;
    }
}
