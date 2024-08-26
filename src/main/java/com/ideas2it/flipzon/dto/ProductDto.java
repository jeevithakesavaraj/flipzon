package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object of Product Entity
 *
 * @author Gokul
 */
@Data
@Builder
@Getter
@Setter
public class ProductDto {

    private Long id;

    private String name;

    private double price;

    private Long brandId;

    private Long categoryId;

    private Long subcategoryId;
}
