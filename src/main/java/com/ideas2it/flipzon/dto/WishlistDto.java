package com.ideas2it.flipzon.dto;

import com.ideas2it.flipzon.model.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * WishlistDto represents wishlist details which related to customer
 * </p>
 *
 * @author Jeevithakesavaraj
 */

@Builder
@Data
public class WishlistDto {
    private long id;
    private Set<Product> products;
    private long customerId;
}
