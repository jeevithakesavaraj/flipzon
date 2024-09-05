package com.ideas2it.flipzon.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ideas2it.flipzon.common.APIResponse;
import com.ideas2it.flipzon.dto.OrderDto;
import com.ideas2it.flipzon.service.OrderService;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    OrderDto orderDto;

    APIResponse apiResponse;

    @BeforeEach
    void setUp() {
        orderDto = OrderDto.builder()
                .id(1L)
                .customerId(1L)
                .addressId(1L)
                .build();
        apiResponse = APIResponse.builder()
                .status(200)
                .data("OK")
                .build();
    }

    @Test
    void placeOrder() {
        when(orderService.placeOrder(orderDto)).thenReturn(orderDto);
        ResponseEntity<OrderDto> response = orderController.placeOrder(orderDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderDto, response.getBody());
        verify(orderService, times(1)).placeOrder(orderDto);
    }

    @Test
    void getOrdersByCustomer() {
        when(orderService.getOrdersByCustomerId(anyLong())).thenReturn(List.of(orderDto));
        ResponseEntity<List<OrderDto>> response = orderController.getOrdersByCustomer(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(orderService, times(1)).getOrdersByCustomerId(anyLong());
    }

    @Test
    void cancelOrder() {
        when(orderService.cancelOrder(anyLong(), anyLong())).thenReturn(orderDto);
        ResponseEntity<OrderDto> response = orderController.cancelOrder(1L, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderDto, response.getBody());
        verify(orderService, times(1)).cancelOrder(anyLong(), anyLong());
    }
}
