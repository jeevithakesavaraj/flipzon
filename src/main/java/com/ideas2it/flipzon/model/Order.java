package com.ideas2it.flipzon.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "payment_Method")
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = "payment_status")
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderedDate;

    private double totalPrice;

    @Column(name = "order_status")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    private boolean isDeleted;
}
