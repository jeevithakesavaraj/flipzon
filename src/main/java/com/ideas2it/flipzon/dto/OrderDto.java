package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

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
}
