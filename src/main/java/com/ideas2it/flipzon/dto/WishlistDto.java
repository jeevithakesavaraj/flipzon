package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

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
    private long customerId;
}
