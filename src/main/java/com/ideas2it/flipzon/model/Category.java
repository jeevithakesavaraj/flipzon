package com.ideas2it.flipzon.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * Category class represents the category of the products.
 * </p>
 *
 * @author Gokul
 */
@Entity
@Table
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private boolean isDeleted;

    @OneToMany(mappedBy = "category")
    private Set<Product> product;

    @OneToMany(mappedBy = "category")
    private Set<Subcategory> subcategories;
}
