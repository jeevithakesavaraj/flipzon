package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.OrderDto;
import com.ideas2it.flipzon.dto.OrderItemDto;
import com.ideas2it.flipzon.model.Order;
import com.ideas2it.flipzon.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Order Mapper has method for conversion of Dto to entity and entity to dto
 * </p>
 *
 * @author Jeevithakesavaraj
 */
public class OrderMapper {

    /**
     * <p>
     * Converts Order to OrderDto
     * </p>
     *
     * @param order {@link Order}
     * @return OrderDto {@link OrderDto}
     */
    public static OrderDto convertEntityToDto(Order order) {
        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItemDtos.add(OrderItemMapper.convertEntityToDto(orderItem));
        }
        return OrderDto.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .orderItems(orderItemDtos)
                .addressId(order.getAddress().getId())
                .paymentStatus(order.getPaymentStatus())
                .paymentType(order.getPaymentType())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
