package com.ideas2it.flipzon.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * <p>
 *     Category class represents the categories.
 * </p>
 *
 * @author Gokul
 */
@Entity
@Table
@Builder
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean isActive;

    @OneToMany(mappedBy = "subCategory")
    @JoinColumn(name = "id")
    private Set<SubCategory> subCategories;

    @OneToMany(mappedBy = "product")
    private Set<Product> products;
}
