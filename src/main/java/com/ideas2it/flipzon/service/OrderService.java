package com.ideas2it.flipzon.service;

import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.common.APIResponse;
import com.ideas2it.flipzon.dto.OrderDto;

import java.util.List;

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
     * @return  savedOrderDto {@link OrderDto}
     */
    OrderDto placeOrder(OrderDto orderDto);

    /**
     * <p>
     * Get orders by customer Id
     * </p>
     * @param customerId  Id of the customer
     * @return list of orderDtos {@link OrderDto}
     */
    List<OrderDto> getOrdersByCustomerId(long customerId);

    /**
     * <p>
     * Cancel order by orderId
     * </p>
     * @param customerId   Id of the customer
     * @param orderId  Id of the order which we want to cancel
     * @return orderDto {@link OrderDto}
     */
    OrderDto cancelOrder(long customerId, long orderId);
}