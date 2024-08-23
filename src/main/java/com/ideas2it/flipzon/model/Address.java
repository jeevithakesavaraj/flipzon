package com.ideas2it.flipzon.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * Address class represents the address of an user.
 * </p>
 *
 * @author Gowthamraj
 */
@Entity
@Data
@Builder
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String addressLine;

    private String city;

    private String pinCode;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private boolean isDeleted;
}
