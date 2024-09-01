package com.ideas2it.flipzon.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Order class represents order details which include product details, payment details and delivery details and whose order is this.
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "payment_Method")
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = "payment_status")
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus;

    private Date orderedDate;

    private double totalPrice;
}
