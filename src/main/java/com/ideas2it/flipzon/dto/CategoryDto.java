package com.ideas2it.flipzon.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object of category Entity
 *
 * @author Gokul
 */
@Builder
@Data
public class CategoryDto {
    private Long id;

    private String name;
}
