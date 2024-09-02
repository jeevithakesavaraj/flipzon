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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.common.APIResponse;
import com.ideas2it.flipzon.dao.OrderDao;
import com.ideas2it.flipzon.dto.OrderDto;
import com.ideas2it.flipzon.model.Address;
import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.model.CartItem;
import com.ideas2it.flipzon.model.Order;
import com.ideas2it.flipzon.model.OrderItem;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.dto.OrderItemDto;
import com.ideas2it.flipzon.mapper.OrderItemMapper;
import com.ideas2it.flipzon.mapper.OrderMapper;

/**
 * <p>
 *  This class implements methods in order service
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    @Autowired
    private APIResponse apiResponse;

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
    public APIResponse placeOrder(OrderDto orderDto) {
        Cart cart = cartService.getCartByCustomerId(orderDto.getCustomerId());
        Address address = addressService.getAddressById(orderDto.getAddress_id());
        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        LocalDate currentDate = LocalDate.now();
        order.setOrderedDate(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        order.setAddress(address);
        order.setPaymentType(orderDto.getPaymentType());
        order.setPaymentStatus(orderDto.getPaymentStatus());
        order.setTotalPrice(cart.getTotalPrice());
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
            stockService.reduceStockByOrder(product.getId(),quantity);
        });
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public APIResponse getOrdersByCustomerId(long customerId) {
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order :  orderDao.findByCustomerIdAndIsDeletedFalse(customerId)) {
            List<OrderItemDto> orderItems = new ArrayList<>();
            for (OrderItem orderItem : order.getOrderItems()) {
                orderItems.add(OrderItemMapper.convertEntityToDto(orderItem));
            }
            OrderDto orderDto = OrderMapper.convertEntityToDto(order);
            orderDto.setOrderItems(orderItems);
            orderDtos.add(orderDto);
        }
        apiResponse.setData(orderDtos);
        apiResponse.setStatus(HttpStatus.OK.value());
         return apiResponse;
    }

    @Override
    public APIResponse cancelOrder(long customerId, long orderId) {
        Order order = orderDao.findByIdAndCustomerId(orderId, customerId);
        order.setDeleted(true);
        orderDao.save(order);
        order.getOrderItems().forEach(orderItem -> {
            int quantity = orderItem.getQuantity();
            Product product = orderItem.getProduct();
            cartService.removeProductFromCart(order.getCustomer().getId(),orderItem.getProduct().getId());
            stockService.updateStockByCancelledOrder(product.getId(),quantity);
        });
        apiResponse.setData("Order Cancelled");
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }
}
