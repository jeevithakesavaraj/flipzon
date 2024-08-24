package com.ideas2it.flipzon.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private double price;

    @Column
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private int initialQuantity;

    @Column
    private int currentQuantity;
}
