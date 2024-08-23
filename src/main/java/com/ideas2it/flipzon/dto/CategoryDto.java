package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object of category Entity
 *
 * @author Gokul
 */
@Builder
@Getter
@Setter
public class CategoryDto {
    private long id;

    private String name;

    private String subcategoryId;
}
