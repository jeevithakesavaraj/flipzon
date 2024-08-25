package com.ideas2it.flipzon.dto;

import java.util.Set;

import lombok.Data;
import lombok.Builder;

/**
 * <p>
 *  CartDto represents cart details
 * </p>
 *
 * @author Jeevithakesavaraj
 */

@Builder
@Data
public class CartDto {
    private Long cartId;
    private Double totalPrice = 0.0;
    private Set<ProductDto> products;
}
