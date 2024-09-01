package com.ideas2it.flipzon.dto;

import com.ideas2it.flipzon.model.PaymentStatus;
import com.ideas2it.flipzon.model.PaymentType;
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
    private long cart_id;
    private long address_id;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
}
