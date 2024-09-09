package com.ideas2it.flipzon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.dto.OrderDto;
import com.ideas2it.flipzon.service.CustomerService;
import com.ideas2it.flipzon.service.OrderService;
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

    @Autowired
    private OrderService orderService;
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

    /**
     * <p>
     * Get all orders
     * </p>
     * @return List of orders {@link OrderDto}
     */
    @GetMapping("/orders")
    public List<OrderDto> getOrders() {
        return orderService.getOrders();
    }
}
