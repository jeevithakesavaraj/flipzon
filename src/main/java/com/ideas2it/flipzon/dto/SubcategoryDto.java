package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object of subcategory Entity
 *
 * @author Gokul
 */
@Builder
@Data
public class SubcategoryDto {
    private Long id;

    private String name;

    private Long categoryId;
}
