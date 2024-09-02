package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDto {

    private long id;
    private long productId;
    private String productName;
    private int quantity;
    private double price;

}
