package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object of OrderedItem Entity
 *
 * @author Gokul
 */
@Builder
@Getter
@Setter
public class OrderItemDto {
    private long id;

    private int quantity;

    private long product_id;

    private double orderedProductPrice;
}
