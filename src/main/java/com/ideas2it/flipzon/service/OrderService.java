package com.ideas2it.flipzon.service;

import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.common.APIResponse;
import com.ideas2it.flipzon.dto.OrderDto;
/**
 * <p>
 * Order Service interface has methods for CRUD operations
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Service
public interface OrderService {

    /**
     * <p>
     *  Place the order
     * </p>
     * @param orderDto  {@link OrderDto}
     * @return  APIResponse {@link APIResponse}
     */
    APIResponse placeOrder(OrderDto orderDto);

    /**
     * <p>
     * Get orders by customer Id
     * </p>
     * @param customerId  Id of the customer
     * @return APIResponse {@link APIResponse}
     */
    APIResponse getOrdersByCustomerId(long customerId);

    /**
     * <p>
     * Cancel order by orderId
     * </p>
     * @param customerId   Id of the customer
     * @param orderId  Id of the order which we want to cancel
     * @return APIResponse {@link APIResponse}
     */
    APIResponse cancelOrder(long customerId, long orderId);
}