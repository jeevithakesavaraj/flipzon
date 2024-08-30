package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object of Stock Entity
 *
 * @author Gokul
 */
@Data
@Builder
public class StockDto {

    private Long id;

    private Long productId;

    private int initialQuantity;

    private int currentQuantity;
}
