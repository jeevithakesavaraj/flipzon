package com.ideas2it.flipzon.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

/**
 * <p>
 * Class Customer represents a customer in the system.
 * </p>
 *
 * @author Gowthamraj
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Wishlist wishlist;

}
