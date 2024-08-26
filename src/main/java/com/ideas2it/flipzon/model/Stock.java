package com.ideas2it.flipzon.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Long id;

    @Column
    private boolean isDeleted;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private int initialQuantity;

    @Column
    private int currentQuantity;
}
