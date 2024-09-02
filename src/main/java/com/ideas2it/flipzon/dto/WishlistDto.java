package com.ideas2it.flipzon.dto;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WishlistDto {
    private long id;

    @NotNull(message = "Please enter Customer Id")
    private Long customerId;

    private Set<Long> productIds;
}
