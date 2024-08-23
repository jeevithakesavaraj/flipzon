package com.ideas2it.flipzon.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * OrderItems class represents the list of ordered products.
 * </p>
 *
 * @author Gokul
 */
@Entity
@Data
@Builder
@Table
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private boolean isDeleted;

    @Column
    private int quantity;

    @Column
    private double orderedProductPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "orderItem")
    private Product product;
}
