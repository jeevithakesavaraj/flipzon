package com.ideas2it.flipzon.dto;

import com.ideas2it.flipzon.model.OrderItem;
import com.ideas2it.flipzon.model.PaymentStatus;
import com.ideas2it.flipzon.model.PaymentType;
import jakarta.validation.constraints.NotBlank;
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
    private long id;

    @NotNull(message = "Customer ID is mandatory")
    @NotBlank(message = "Please enter the customer id")
    private long customerId;

    @NotNull(message = "Cart ID is mandatory")
    @NotBlank(message = "Please enter the cart id")
    private long cart_id;

    @NotNull(message = "Address ID is mandatory")
    @NotBlank(message = "Please enter the address id")
    private long address_id;

    private List<OrderItemDto> orderItems;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
}
