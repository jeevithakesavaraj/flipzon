package com.ideas2it.flipzon.dto;

import lombok.*;

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

    private long id;

    private String name;

    private double price;

    private long brandId;

    private long categoryId;

    private String subcategory;
}
