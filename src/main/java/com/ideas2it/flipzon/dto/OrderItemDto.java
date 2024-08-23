package com.ideas2it.flipzon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer object of OrderedItem Entity
 *
 * @author Gokul
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private long id;

    private int quantity;

    private double orderedProductPrice;
}
