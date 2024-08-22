package com.ideas2it.flipzon.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table
@Builder
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private double price;

    @Column
    private int quantity;

    @Column
    private boolean isDeleted;

//    @OneToMany(mappedBy = "wishlist")
//    private Wishlist wishlist;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Category subcategory;

    @ManyToOne
    @JoinColumn(name = "orderItems_id")
    private OrderItems orderItems;
}
