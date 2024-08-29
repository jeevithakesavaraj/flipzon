package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CartResponseDto {

    private String customerName;

    private Set<String> products;

    private double productPrice;

    private int quantity;

    private double totalPrice;

}
