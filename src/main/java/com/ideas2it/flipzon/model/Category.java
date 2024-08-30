package com.ideas2it.flipzon.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Getter
@Setter
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
    @JsonIgnore
    private Set<Product> product;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<Subcategory> subcategories;
}
