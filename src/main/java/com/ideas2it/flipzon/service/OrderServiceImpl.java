package com.ideas2it.flipzon.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.common.APIResponse;
import com.ideas2it.flipzon.dao.OrderDao;
import com.ideas2it.flipzon.dto.OrderDto;
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

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderDao orderDao;

//    public APIResponse addOrder(OrderDto orderDto) {
//        APIResponse apiResponse = new APIResponse();
//        logger.debug("Getting list of orders to check previous carts.");
//        for (Order order : orders) {
//            if (Objects.equals(order.getCart().getCartId(), orderDto.getCartId())) {
//                logger.warn("Order with cart id : {} already exists.", orderDto.getCartId());
//                throw new EntityAlreadyExistsException("Order with cart id : " + orderDto.getCartId() + " already exists.");
//            }
//        }
//        Cart cart = cartService.getCartAsModel(orderDto.getCartId());
//        Order order = OrderMapper.convertToOrder(orderDto);
//        String otp = String.valueOf(OtpGenerator.generateOtp());
//        Double sum = 0.0;
//        for (CartItem cartItem : cart.getCartItems()) {
//            sum += cartItem.getTotalPrice();
//        }
//        order.setCart(cart);
//        order.setOrderAmount(sum);
//        order.setOtp(encoder.encode(otp));
//        Order resultOrder = orderRepository.save(order);
//        apiResponse.setData(OrderMapper.convertToOrderDto(resultOrder));
//        apiResponse.setStatus(HttpStatus.OK.value());
//        logger.debug("Checking payment method and amount to assign order.");
//        if ((sum > 50.0) && ((resultOrder.getPaymentMethod().equals(PaymentMethod.UPI)) ||
//                (resultOrder.getPaymentMethod().equals(PaymentMethod.CASHON)))) {
//            orderAssignService.addOrderAssign(resultOrder);
//        }
//        stockService.reduceStocks(resultOrder.getCart().getCartItems());
//        String subject = "Order Acknowledgement";
//        String body = "Your order " + resultOrder.getOrderId() + " has been successfully placed."
//                + "Your One Time Password for the order verification is "
//                + otp;
//        emailSenderService.sendEmail(resultOrder.getCart().getCustomer().getUser().getEmailId(), subject, body);
//        cartService.addCart(resultOrder.getCart().getCustomer());
//        return apiResponse;
//    }
}
