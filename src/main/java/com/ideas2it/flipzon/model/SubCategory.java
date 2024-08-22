package com.ideas2it.flipzon.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * <p>
 *     SubCategory class represents the subcategories.
 * </p>
 *
 * @author Gokul
 */
@Entity
@Table
@Builder
@Data
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean isActive;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<Product> products;
}
