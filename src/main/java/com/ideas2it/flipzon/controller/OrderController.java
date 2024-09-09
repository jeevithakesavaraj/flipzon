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

    /**
     * <p>
     * Place the order
     * </p>
     * @param orderDto {@link OrderDto}
     * @return APIResponse {@link APIResponse}
     */
    @PostMapping("/{id}/orders")
    public ResponseEntity<OrderDto> placeOrder(@Valid @PathVariable Long id, @RequestBody OrderDto orderDto) {
        System.out.println("order");
        OrderDto savedOrderDto = orderService.placeOrder(id, orderDto);
        return ResponseEntity.ok(savedOrderDto);
    }

    /**
     * <p>
     * Get the list of orders by customer Id
     * </p>
     * @param customerId Id of the customer whose order history we want
     * @return APIResponse {@link APIResponse}
     */
    @GetMapping("/{customerId}/orders")
    public ResponseEntity<List<OrderDto>> getOrdersByCustomer(@PathVariable long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }

    /**
     * <p>
     * Cancel the order by customer Id and order Id
     * </p>
     * @param customerId Id of the customer whose order we want to cancel
     * @param orderId  Id of the order which we want to cancel
     * @return APIResponse {@link APIResponse}
     */
    @DeleteMapping("/{customerId}/orders/cancelorder/{orderId}")
    public ResponseEntity<OrderDto> cancelOrder(@PathVariable long customerId, @PathVariable long orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(customerId, orderId));
    }
}
