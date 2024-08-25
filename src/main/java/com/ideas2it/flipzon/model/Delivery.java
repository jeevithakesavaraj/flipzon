package com.ideas2it.flipzon.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * Delivery class represents the details of the delivery partner.
 * </p>
 *
 * @author Gowthamraj
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
