package com.ideas2it.flipzon.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * Role class represents the role within the system.
 * </p>
 *
 * @author Gowthamraj
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private UserRole name;

    @Builder
    public Role(UserRole rolename) {
        this.name = rolename;
    }
}
