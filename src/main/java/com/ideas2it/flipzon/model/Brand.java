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
 * Brand class represents the brand of the products.
 * </p>
 *
 * @author Gokul
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private boolean isDeleted;

    @OneToMany(mappedBy = "brand")
    @JsonIgnore
    private Set<Product> products;
}
