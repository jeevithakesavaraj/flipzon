package com.ideas2it.flipzon.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * <p>
 * CartItem represents cart item details
 * </p>
 *
 * @author Jeevithakesavaraj
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cartItem")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @ManyToOne
//    @JoinColumn(name = "cart_id")
//    private Cart cart;

    @OneToMany
    private Set<Product> products;

    private int quantity;
    private double price;
    private double totalPrice;
}
