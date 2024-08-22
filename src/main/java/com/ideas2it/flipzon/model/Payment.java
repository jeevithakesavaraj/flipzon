package com.ideas2it.flipzon.model;

import jakarta.persistence.*;
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
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String method;

    private String status;

    @OneToOne(mappedBy = "payment")
    private Order order;
}
