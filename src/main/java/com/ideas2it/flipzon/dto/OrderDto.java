package com.ideas2it.flipzon.dto;

import com.ideas2it.flipzon.model.OrderStatus;
import com.ideas2it.flipzon.model.PaymentStatus;
import com.ideas2it.flipzon.model.PaymentType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
    private Long id;

    @NotNull(message = "Customer ID is mandatory")
    private Long customerId;

    @NotNull(message = "Address ID is mandatory")
    private Long addressId;

    private List<OrderItemDto> orderItems;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private OrderStatus orderStatus;
}
