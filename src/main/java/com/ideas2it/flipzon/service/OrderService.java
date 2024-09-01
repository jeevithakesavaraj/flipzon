package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.common.APIResponse;
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
     * @param orderDto  {@link OrderDto}
     * @return  APIResponse {@link APIResponse}
     */
    APIResponse placeOrder(OrderDto orderDto);
}