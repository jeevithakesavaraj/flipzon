package com.ideas2it.flipzon.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * Wishlist class represents where user(customer) can add and remove the products as per their wish.
 * </p>
 *
 * @author JeevithaKesavaraj
 */
@Entity
@Data
@Builder
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    @JoinColumn(name = "wishlist_id")
    private Set<Product> products;

    @OneToOne(mappedBy = "wishlist")
    private User user;
}
