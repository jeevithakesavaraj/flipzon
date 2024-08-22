package com.ideas2it.flipzon.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

/**
 * <p>
 *     Brand class represents the available brands.
 * </p>
 *
 * @author Gokul
 */
@Entity
@Table
@Data
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean isActive;

    @OneToMany(mappedBy = "product")
    private Set<Product> products;
}
