package com.ideas2it.flipzon.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.flipzon.dto.OrderDto;
import com.ideas2it.flipzon.helper.JwtHelper;
import com.ideas2it.flipzon.service.CustomerService;
import com.ideas2it.flipzon.service.OrderService;

/**
 * <p>
 * Order controller is the controller for order operations of the customer
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@RestController
@RequestMapping("flipzon/api/v1/customers")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    /**
     * <p>
     * Place the order
     * </p>
     *
     * @param orderDto {@link OrderDto}
     * @return {@link OrderDto}
     */
    @PostMapping("/me/orders")
    public ResponseEntity<OrderDto> placeOrder(@Valid @RequestBody OrderDto orderDto) {
        long customerId = customerService.getCustomerIdByUserName(JwtHelper.extractUserNameFromToken());
        OrderDto savedOrderDto = orderService.placeOrder(customerId, orderDto);
        return ResponseEntity.ok(savedOrderDto);
    }

    /**
     * <p>
     * Get the list of orders by customer Id
     * </p>
     *
     * @return list of orders {@link OrderDto}
     */
    @GetMapping("/me/orders")
    public ResponseEntity<List<OrderDto>> getOrdersByCustomer() {
        long customerId = customerService.getCustomerIdByUserName(JwtHelper.extractUserNameFromToken());
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }

    /**
     * <p>
     * Cancel the order by customer Id and order Id
     * </p>
     *
     * @param orderId  Id of the order which we want to cancel
     * @return orderDto {@link OrderDto}
     */
    @DeleteMapping("/me/orders/{orderId}")
    public ResponseEntity<OrderDto> cancelOrder(@PathVariable long orderId) {
        long customerId = customerService.getCustomerIdByUserName(JwtHelper.extractUserNameFromToken());
        return ResponseEntity.ok(orderService.cancelOrder(customerId, orderId));
    }
}
