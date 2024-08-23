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
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private long id;

    private String name;

    private double price;

    private int quantity;

    private String brandId;

    private String categoryId;

//    private String parentId;

}
