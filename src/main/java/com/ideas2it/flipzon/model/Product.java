package com.ideas2it.flipzon.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * <p>
 *     Product class represents the details of products.
 * </p>
 *
 * @author Gokul
 */
@Entity
@Table
@Builder
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean isActive;

    private double price;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id")
    private SubCategory subCategory;

    @ManyToOne
    @JoinColumn(name = "id")
    private Brand brand;
}
