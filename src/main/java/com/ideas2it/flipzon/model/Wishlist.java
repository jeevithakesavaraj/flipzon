package com.ideas2it.flipzon.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *  Wishlist class represents where customer can add and remove the products as per their wish.
 * </p>
 *
 * @author Gowthamraj
 */

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wishlists")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany
    private Set<Product> products;
}
