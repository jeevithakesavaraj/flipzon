package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.OrderDto;
import com.ideas2it.flipzon.model.Order;

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
        return OrderDto.builder()
                .id(order.getId())
                .address_id(order.getAddress().getId())
                .build();
    }
}
