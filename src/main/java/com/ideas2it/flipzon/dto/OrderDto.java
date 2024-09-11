package com.ideas2it.flipzon.dto;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import com.ideas2it.flipzon.model.OrderStatus;
import com.ideas2it.flipzon.model.PaymentStatus;
import com.ideas2it.flipzon.model.PaymentType;

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

    @NotBlank(message = "Payment Type is mandatory (UPI/CASH_ON")
    private PaymentType paymentType;

    @NotBlank(message = "Payment status is mandatory (PAID/UNPAID)")
    private PaymentStatus paymentStatus;

    private double totalPrice;
    private OrderStatus orderStatus;
    private Date orderedDate;
}
