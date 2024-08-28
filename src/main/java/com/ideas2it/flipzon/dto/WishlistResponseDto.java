package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class WishlistResponseDto {
    private String customerName;

    private Set<String> products;
}
