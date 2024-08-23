package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * PaymentDto represents payment details.
 * </p>
 *
 * @author Jeevithakesavaraj
 */

@Builder
@Data
public class PaymentDto {
    private long id;
    private String transactionId;
    private String cardDetails;
    private String status;
}
