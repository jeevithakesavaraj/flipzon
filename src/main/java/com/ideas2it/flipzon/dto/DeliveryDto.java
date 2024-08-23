package com.ideas2it.flipzon.dto;

import com.ideas2it.flipzon.model.Order;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * DeliveryDto is a Data Transfer Object that represents a Delivery.
 * It contains fields for transferring delivery-related data between layers of the application.
 * </p>
 *
 * @author Gowthamraj
 */
@Data
@Builder
public class DeliveryDto {

    private long id;

    private String idProof;

    private Order order;
}
