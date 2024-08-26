package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object of subcategory Entity
 *
 * @author Gokul
 */
@Builder
@Getter
@Setter
public class SubcategoryDto {
    private Long id;

    private String name;

    private Long categoryId;

}
