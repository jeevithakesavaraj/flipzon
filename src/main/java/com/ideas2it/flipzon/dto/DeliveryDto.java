package com.ideas2it.flipzon.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    private String name;

    @Email
    private String email;

    @NotNull
    private String password;

    private String phoneNumber;
    private String idProof;
}
