package com.ideas2it.flipzon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer object of category Entity
 *
 * @author Gokul
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private long id;

    private String name;

    private String subcategoryId;
}
