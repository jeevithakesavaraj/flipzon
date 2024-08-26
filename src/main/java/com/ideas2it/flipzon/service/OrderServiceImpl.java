package com.ideas2it.flipzon.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ideas2it.flipzon.dao.OrderDao;
import com.ideas2it.flipzon.dto.OrderDto;
import com.ideas2it.flipzon.mapper.OrderMapper;
import com.ideas2it.flipzon.model.Order;

public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    public OrderDto addOrder(OrderDto orderDto) {
        Order savedOrder = orderDao.save(OrderMapper.convertDtoToEntity(orderDto));
        return OrderMapper.convertEntityToDto(savedOrder);
    }
}
