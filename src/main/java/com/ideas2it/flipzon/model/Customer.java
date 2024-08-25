package com.ideas2it.flipzon.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * Class Customer represents a customer in the system.
 * </p>
 *
 * @author Gowthamraj
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> addresses = new HashSet<>();

    private boolean isDeleted;

    @OneToOne(mappedBy = "customer")
    private Cart cart;

    @OneToOne(mappedBy = "customer")
    private Wishlist wishlist;
}
