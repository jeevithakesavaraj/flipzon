package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.OrderItemDto;
import com.ideas2it.flipzon.model.OrderItem;

/**
 * <p>
 *  This class represents conversion of Entity to Dto
 * </p>
 *
 * @author Jeevithakesavaraj
 */
public class OrderItemMapper {

    /**
     * <p>
     * Convert OrderItem to OrderItemDto
     * </p>
     * @param orderItem  {@link OrderItem}
     * @return orderItemDto {@link OrderItemDto}
     */
    public static OrderItemDto convertEntityToDto(OrderItem orderItem) {
        return OrderItemDto.builder()
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProduct().getName())
                .price(orderItem.getOrderedProductPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }
}
