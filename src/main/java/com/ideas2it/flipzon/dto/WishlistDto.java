package com.ideas2it.flipzon.dto;

import com.ideas2it.flipzon.model.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
    private List<Product> products;
    private long customerId;
}
