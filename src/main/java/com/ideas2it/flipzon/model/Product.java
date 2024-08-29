package com.ideas2it.flipzon.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

/**
 * <p>
 * Product class represents the products.
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
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private boolean isDeleted;

    @Column
    private double price;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    @JsonIgnore
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    @JsonIgnore
    private Subcategory subcategory;

    @OneToOne(mappedBy = "product")
    private Stock stock;
}
