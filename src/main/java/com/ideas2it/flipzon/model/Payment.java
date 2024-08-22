package com.ideas2it.flipzon.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * Payment class represents payment details which made by customer who orders the products
 * </p>
 *
 * @author Jeevithakesavaraj
 */

@Entity
@Data
@Builder
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String transaction_id;

    private String cardDetails;

    private String status;
}
