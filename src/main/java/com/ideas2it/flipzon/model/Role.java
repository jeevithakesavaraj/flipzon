package com.ideas2it.flipzon.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * <p>
 * The Role class represents the role of a user.
 * </p>
 *
 * @author Gowthamraj
 */
@Entity
@Data
@Builder
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany(mappedBy = "privileges", cascade = CascadeType.ALL)
    private Set<Privilege> privileges;
}
