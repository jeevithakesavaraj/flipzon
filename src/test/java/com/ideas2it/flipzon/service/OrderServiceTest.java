package com.ideas2it.flipzon.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ideas2it.flipzon.dao.OrderDao;
import com.ideas2it.flipzon.dto.OrderDto;
import com.ideas2it.flipzon.model.Address;
import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.model.CartItem;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Order;
import com.ideas2it.flipzon.model.OrderItem;
import com.ideas2it.flipzon.model.PaymentStatus;
import com.ideas2it.flipzon.model.PaymentType;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.model.User;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderDao orderDao;

    @Mock
    private CartService cartService;

    @Mock
    private AddressService addressService;

    @Mock
    private OrderItemService orderItemService;

    @Mock
    private StockService stockService;

    @Mock
    private APIResponse apiResponse;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    private Customer customer;
    private User user;
    private Cart cart;
    private Address address;
    private OrderItem orderItem;
    private Order order;
    private CartItem cartItem;
    private OrderDto orderDto;
    private Set<CartItem> cartItems;
    private List<OrderItem> orderItems;
    private List<Order> orderList;
    private Product product;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .id(1L)
                .user(user)
                .build();
        user = User.builder()
                .id(1L)
                .name("Hermoine")
                .email("hermoine@xampp.com")
                .build();
        product = Product.builder()
                .id(1L)
                .name("Product A")
                .build();

        cartItem = CartItem.builder()
                .product(product)
                .quantity(2)
                .price(100.0)
                .build();

        cartItems = new HashSet<>();
        cartItems.add(cartItem);

        cart = Cart.builder()
                .customer(Customer.builder().id(1L).build())
                .cartItems(cartItems)
                .totalPrice(200.0)
                .build();

        address = Address.builder()
                .id(1L)
                .addressLine("123 Main Rd")
                .city("Salem")
                .pinCode("636010")
                .build();

        orderItem = OrderItem.builder()
                .product(product)
                .quantity(2)
                .orderedProductPrice(100.0)
                .build();

        orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        order = Order.builder()
                .customer(cart.getCustomer())
                .orderedDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .address(address)
                .paymentType(PaymentType.UPI)
                .paymentStatus(PaymentStatus.PAID)
                .totalPrice(cart.getTotalPrice())
                .orderItems(orderItems)
                .build();

        orderList = new ArrayList<>();
        orderList.add(order);

        orderDto = OrderDto.builder()
                .customerId(1L)
                .paymentType(PaymentType.UPI)
                .paymentStatus(PaymentStatus.PAID)
                .build();
    }

    @Test
    void placeOrder() {
        when(cartService.getCartByCustomerId(anyLong())).thenReturn(cart);
        when(addressService.getAddressById(anyLong())).thenReturn(address);
        when(orderDao.save(any(Order.class))).thenReturn(order);
        when(orderItemService.addOrderItems(anyList())).thenReturn(orderItems);
        OrderDto placeOrder = orderServiceImpl.placeOrder(1L, orderDto);
        assertNotNull(placeOrder);
        assertEquals(placeOrder.getId(),order.getId());
        verify(orderDao, times(1)).save(any(Order.class));

    }

    @Test
    void testGetOrdersByCustomerId() {
        when(orderDao.findByCustomerIdAndIsDeletedFalse(anyLong())).thenReturn(List.of(order));
        List<OrderDto> response = orderServiceImpl.getOrdersByCustomerId(1L);
        assertNotNull(response);
        assertEquals(response.getFirst().getId(), order.getId());
        verify(orderDao, times(1)).findByCustomerIdAndIsDeletedFalse(anyLong());
    }

    @Test
    void testCancelOrder() {
        when(orderDao.findByIdAndCustomerId(anyLong(), anyLong())).thenReturn(order);
        when(orderDao.save(any(Order.class))).thenReturn(order);
        OrderDto cancelledOrder = orderServiceImpl.cancelOrder(1L, 1L);
        assertNotNull(cancelledOrder);
        verify(orderDao, times(1)).save(any(Order.class));
    }
}