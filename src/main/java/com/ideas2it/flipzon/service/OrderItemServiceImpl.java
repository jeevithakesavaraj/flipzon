package com.ideas2it.flipzon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.OrderItemDao;
import com.ideas2it.flipzon.model.OrderItem;

/**
 * <p>
 *  OrderServiceImpl has methods implementation for order service interface.
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;

    @Override
    public List<OrderItem> addOrderItems(List<OrderItem> orderItems) {
        return orderItemDao.saveAll(orderItems);
    }
}
