package com.ideas2it.flipzon.dto;

import com.ideas2it.flipzon.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class StockDto {

    private long id;

    private double price;

    private Product product;

    private int initialQuantity;

    private int currentQuantity;
}
