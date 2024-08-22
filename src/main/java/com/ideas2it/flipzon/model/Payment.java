package com.ideas2it.flipzon.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * Payment class represents payment details which is made by user(customer) for purchasing products.
 * </p>
 *
 * @author JeevithaKesavaraj
 */

@Entity
@Data
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String method;

    private String status;

    @OneToOne(mappedBy = "payment")
    private Order order;
}
