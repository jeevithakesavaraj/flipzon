package com.ideas2it.flipzon.model;

import java.util.Set;

import jakarta.persistence.*;
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
    private double orderedProductsPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "orderItem")
    private Product product;
}
