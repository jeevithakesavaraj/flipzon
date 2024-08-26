package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

import com.ideas2it.flipzon.model.Order;
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
}
