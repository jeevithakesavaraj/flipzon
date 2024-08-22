package com.ideas2it.flipzon.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * Cart class represents where user can add and remove the products if user wants to buy the products
 * </p>
 *
 * @author JeevithaKesavaraj
 */
@Entity
@Data
@Builder
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    @JoinColumn(name = "cart_id")
    private Set<Product> products;

    @OneToOne(mappedBy = "cart")
    private User user;
}
