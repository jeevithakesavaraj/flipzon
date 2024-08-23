package com.ideas2it.flipzon.model;

import java.util.Set;

import jakarta.persistence.*;
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

    @OneToMany
    @JoinColumn(name = "address_id")
    private Set<Address> addresses;

    private boolean isDeleted;
}
