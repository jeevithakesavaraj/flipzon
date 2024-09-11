package com.ideas2it.flipzon.dto;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Address ID is mandatory")
    private Long addressId;

    private List<OrderItemDto> orderItems;

    @NotBlank
    @Size(min = 3, max = 6, message = "Payment method should contain 3 to 6 letters.")
    @Pattern(regexp = "^(UPI|CASHON)$", message = "Payment method should contain either UPI or CASHON.")
    private String paymentType;

    private String paymentStatus;

    private double totalPrice;
    private OrderStatus orderStatus;
    private Date orderedDate;
}
