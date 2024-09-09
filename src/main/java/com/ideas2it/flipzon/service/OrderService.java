package com.ideas2it.flipzon.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
     * @param id : customer id
     * @param orderDto  {@link OrderDto}
     * @return  savedOrderDto {@link OrderDto}
     */
    OrderDto placeOrder(Long id, OrderDto orderDto);

    /**
     * <p>
     * Get orders by customer Id
     * </p>
     * @param customerId : customer id
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

    /**
     * <p>
     * Get all orders
     * </p>
     * @return List of orders {@link OrderDto}
     */
    List<OrderDto> getOrders();
}