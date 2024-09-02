package com.ideas2it.flipzon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * <p>
 * CartItem represents cart item details
 * </p>
 *
 * @author Jeevithakesavaraj
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonManagedReference
    private Cart cart;

    @OneToOne
    private Product product;

    private int quantity;

    private double price;

    private double totalPrice;

    public double getTotalPrice() {
        return getQuantity() * getPrice();
    }
}
