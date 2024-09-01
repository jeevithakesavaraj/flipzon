package com.ideas2it.flipzon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.model.OrderItem;

/**
 * <p>
 * Order Item service manages crud operations for order item
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Service
public interface OrderItemService {

    /**
     * <p>
     * Add the list of products which we have ordered
     * </p>
     * @param orderItems Product which we have ordered with price and quantity details
     * @return List<OrderItem>  {@link OrderItem}
     */
    List<OrderItem> addOrderItems(List<OrderItem> orderItems);
}
