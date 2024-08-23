package com.ideas2it.flipzon.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * Order class represents order details which include product details, payment details and delivery details and whose order is this.
 * </p>
 */
@Entity
@Data
@Builder
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private Set<OrderItem> orderItems;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
