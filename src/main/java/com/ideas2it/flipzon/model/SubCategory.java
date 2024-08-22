package com.ideas2it.flipzon.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

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
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "subCategory")
    private Set<Product> products;
}
