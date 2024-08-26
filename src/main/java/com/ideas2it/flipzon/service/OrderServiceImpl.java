package com.ideas2it.flipzon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.OrderDao;
import com.ideas2it.flipzon.dto.OrderDto;
import com.ideas2it.flipzon.mapper.OrderMapper;
import com.ideas2it.flipzon.model.Order;

/**
 * <p>
 *  This class implements methods in order service
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public OrderDto addOrder(OrderDto orderDto) {
        Order savedOrder = orderDao.save(OrderMapper.convertDtoToEntity(orderDto));
        return OrderMapper.convertEntityToDto(savedOrder);
    }
}
