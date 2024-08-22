package com.ideas2it.flipzon.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * The User class represents a user with all of their details who uses the application.
 * </p>
 *
 * @author Gowthamraj
 */
@Entity
@Data
@Builder
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String phoneNumber;

    private boolean isActive;

    @ManyToMany
    @JoinTable(
            name = "user_address",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private Set<Address> addresses;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}
