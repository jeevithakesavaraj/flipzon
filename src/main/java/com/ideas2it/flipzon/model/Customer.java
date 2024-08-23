package com.ideas2it.flipzon.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * Class Customer represents a customer in the system.
 * </p>
 *
 * @author Gowthamraj
 */
@Entity
@Data
@Table
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "customer")
    private Set<Address> addresses;

    private boolean isDeleted;

    @OneToOne(mappedBy = "customer")
    private Cart cart;

    @OneToOne(mappedBy = "customer")
    private Wishlist wishlist;
}
