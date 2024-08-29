package com.ideas2it.flipzon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.service.CustomerService;

/**
 * <p>
 * Admin controller is for admin-related operations
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@RestController
@RequestMapping("flipzon/api/v1/admin")
public class AdminController {

    @Autowired
    private CustomerService customerService;

    /**
     * <p>
     * Get all customers
     * </p>
     * @return  List of customer {@link CustomerDto}
     */
    @GetMapping("/customers")
    public List<CustomerDto> getAllCustomers () {
        return customerService.getAllCustomers();
    }
}
