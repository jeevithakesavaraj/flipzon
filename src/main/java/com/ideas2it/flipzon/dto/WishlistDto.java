package com.ideas2it.flipzon.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WishlistDto {
    private long id;

    private Long customerId;

    private Set<Long> productIds;
}
