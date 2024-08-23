package com.ideas2it.flipzon.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 *  Wishlist class represents where customer can add and remove the products as per their wish.
 * </p>
 *
 * @author JeevithaKesavaraj
 */

@Entity
@Data
@Builder
@Table(name = "wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "wishlist")
    private Set<Product> products;
}
