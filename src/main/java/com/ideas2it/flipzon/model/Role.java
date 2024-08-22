package com.ideas2it.flipzon.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

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
