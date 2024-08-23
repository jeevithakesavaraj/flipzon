package com.ideas2it.flipzon.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * Delivery class represents the details of the delivery partner.
 * </p>
 *
 * @author Gowthamraj
 */
@Entity
@Data
@Table
@Builder
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String idProof;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Order order;
}
