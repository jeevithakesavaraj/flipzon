package com.ideas2it.flipzon.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * WishlistDto is a Data Transfer Object that represents a Wishlist.
 * It contains fields for transferring wishlist-related data between layers of the application.
 * </p>
 *
 * @author Gowthamraj
 */
@Data
@Builder
public class WishlistDto {
    private long id;

    private Long customerId;

    private Set<Long> productIds;
}
