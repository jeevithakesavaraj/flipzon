package com.ideas2it.flipzon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Please enter initial quantity")
    private int initialQuantity;

    private int currentQuantity;
}
