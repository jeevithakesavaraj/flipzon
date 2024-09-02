package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * <p>
 * This represents for token which is response back to the user
 * </p>
 *
 * @author Gowthamraj
 */
@Builder
@Data
public class WishlistResponseDto {
    private String customerName;

    private Set<String> products;
}
