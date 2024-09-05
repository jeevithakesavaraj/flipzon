package com.ideas2it.flipzon.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.OrderDao;
import com.ideas2it.flipzon.dto.OrderDto;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.dto.OrderItemDto;
import com.ideas2it.flipzon.mapper.OrderItemMapper;
import com.ideas2it.flipzon.mapper.OrderMapper;
import com.ideas2it.flipzon.model.Address;
import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.model.CartItem;
import com.ideas2it.flipzon.model.Order;
import com.ideas2it.flipzon.model.OrderItem;
import com.ideas2it.flipzon.model.OrderStatus;
import com.ideas2it.flipzon.model.Product;
/**
 * <p>
 *  This class implements methods in order service
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CartService cartService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private StockService stockService;

    @Override
    public OrderDto placeOrder(OrderDto orderDto) {
        Cart cart = cartService.getCartByCustomerId(orderDto.getCustomerId());
        Address address = addressService.getAddressById(orderDto.getAddressId());
        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        LocalDate currentDate = LocalDate.now();
        order.setOrderedDate(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        order.setAddress(address);
        order.setPaymentType(orderDto.getPaymentType());
        order.setPaymentStatus(orderDto.getPaymentStatus());
        order.setTotalPrice(cart.getTotalPrice());
        order.setOrderStatus(OrderStatus.PLACED);
        LOGGER.info("Order is placed for the customer{}", order.getCustomer().getId());
        Order savedOrder = orderDao.save(order);
        Set<CartItem> cartItems = cart.getCartItems();
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrderedProductPrice(cartItem.getPrice());
            orderItem.setOrder(savedOrder);
            orderItems.add(orderItem);
        }
        orderItems = orderItemService.addOrderItems(orderItems);
        cart.getCartItems().forEach(cartItem -> {
            int quantity = cartItem.getQuantity();
            Product product = cartItem.getProduct();
            cartService.removeProductFromCart(cart.getCustomer().getId(),cartItem.getProduct().getId());
            LOGGER.info("Stock is reduced based on the quantity of the product that customer ordered{}", product.getId());
            stockService.reduceStockByOrder(product.getId(),quantity);
        });
        return OrderMapper.convertEntityToDto(savedOrder);
    }

    @Override
    public List<OrderDto> getOrdersByCustomerId(long customerId) {
        List<Order> placedOrders = orderDao.findByCustomerIdAndIsDeletedFalse(customerId);
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order :  placedOrders) {
            List<OrderItemDto> orderItems = new ArrayList<>();
            for (OrderItem orderItem : order.getOrderItems()) {
                orderItems.add(OrderItemMapper.convertEntityToDto(orderItem));
            }
            OrderDto orderDto = OrderMapper.convertEntityToDto(order);
            orderDto.setOrderItems(orderItems);
            orderDtos.add(orderDto);
        }
        LOGGER.info("Getting the history of orders for the customer ID: {}", customerId);
        return orderDtos;
    }

    @Override
    public OrderDto cancelOrder(long customerId, long orderId) {
        Order order = orderDao.findByIdAndCustomerId(orderId, customerId);
        if (null == order) {
            LOGGER.warn("No order is found in this Id: {}", orderId);
            throw new ResourceNotFoundException("No order is found for this Id: ", orderId);
        }
        for (OrderItem orderItem : order.getOrderItems()) {
            int quantity = orderItem.getQuantity();
            Product product = orderItem.getProduct();
            cartService.removeProductFromCart(order.getCustomer().getId(),orderItem.getProduct().getId());
            stockService.updateStockByCancelledOrder(product.getId(),quantity);
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        order.setDeleted(true);
        Order deletedOrder = orderDao.save(order);
        LOGGER.info("Order is cancelled in this Id:{} for the customer :{} ", orderId, order.getCustomer().getId());
        return OrderMapper.convertEntityToDto(deletedOrder);
    }
}
