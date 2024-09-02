package com.ideas2it.flipzon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.flipzon.common.APIResponse;
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
    @PostMapping("/placeOrder")
    public ResponseEntity<APIResponse> placeOrder(@RequestBody OrderDto orderDto) {
        APIResponse apiResponse = orderService.placeOrder(orderDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * Get the list of orders by customer Id
     * </p>
     * @param customerId Id of the customer whose order history we want
     * @return APIResponse {@link APIResponse}
     */
    @GetMapping("/{customerId}/orders")
    public ResponseEntity<APIResponse> getOrdersByCustomer(@PathVariable long customerId) {
        APIResponse apiResponse = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * Cancel the order by customer Id and order Id
     * </p>
     * @param customerId Id of the customer whose order we want to cancel
     * @param orderId  Id of the order which we want to cancel
     * @return APIResponse {@link APIResponse}
     */
    @DeleteMapping("/{customerId}/orders/cancelOrder/{orderId}")
    public ResponseEntity<APIResponse> cancelOrder(@PathVariable long customerId, @PathVariable long orderId) {
        APIResponse apiResponse = orderService.cancelOrder(customerId, orderId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }
}
