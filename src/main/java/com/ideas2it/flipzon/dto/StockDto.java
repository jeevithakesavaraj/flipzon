package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class StockDto {

    private Long id;

    private Long productId;

    private int initialQuantity;

    private int currentQuantity;
}
