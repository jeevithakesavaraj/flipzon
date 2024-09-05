package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dao.OrderItemDao;
import com.ideas2it.flipzon.model.OrderItem;
import com.ideas2it.flipzon.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {

    @Mock
    private OrderItemDao orderItemDao;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    private List<OrderItem> orderItemList;
    private OrderItem orderItem;

    @BeforeEach
    void setUp() {
        Product product = Product.builder()
                .id(1L)
                .name("Product A")
                .price(100.0)
                .build();

        orderItem = OrderItem.builder()
                .id(1L)
                .product(product)
                .quantity(2)
                .orderedProductPrice(200.0)
                .build();

        orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);
    }

    @Test
    void testAddOrderItems_Success() {
        when(orderItemDao.saveAll(anyList())).thenReturn(orderItemList);
        List<OrderItem> savedOrderItems = orderItemService.addOrderItems(orderItemList);
        assertEquals(orderItemList.size(), savedOrderItems.size());
        assertEquals(orderItemList.get(0).getProduct().getId(), savedOrderItems.get(0).getProduct().getId());
    }
}

