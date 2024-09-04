package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dao.OrderItemDao;
import com.ideas2it.flipzon.model.OrderItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTest {
    @Mock
    private OrderItemDao orderItemDao;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    List<OrderItem> orderItems;

    OrderItem orderItem;

    @Test
    void testAddOrderItems() {
    }
}
