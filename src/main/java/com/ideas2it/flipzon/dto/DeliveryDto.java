package com.ideas2it.flipzon.dto;

import com.ideas2it.flipzon.model.Order;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryDto {

    private long id;

    private String idProof;

    private Order order;
}
