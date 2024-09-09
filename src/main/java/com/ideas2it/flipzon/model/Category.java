package com.ideas2it.flipzon.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * <p>
 * Category class represents the category of the products.
 * </p>
 *
 * @author Gokul
 */
@Entity
@Table(name = "categories")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private boolean isDeleted;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<Product> product;

    @ManyToOne
    @JoinColumn(name = "brandId")
    @JsonIgnore
    private Brand brand;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<Subcategory> subcategories;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @LastModifiedDate
    private Date modifiedDate;
}
