package com.ideas2it.flipzon.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table
@Builder
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "category")
    private List<Category> subcategories;
}
