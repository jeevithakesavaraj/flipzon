package com.ideas2it.flipzon.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

import com.ideas2it.flipzon.model.OrderItem;

/**
 * <p>
 * OrderDto represents order details.
 * </p>
 *
 * @author Jeevithakesavaraj
 */

@Data
@Builder
public class OrderDto {
    private long id;
    private long customerId;
    private double totalPrice;
    private long payment_id;
    private long address_id;
    private Set<OrderItem> orderItems;
}
