package com.ideas2it.flipzon.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import com.ideas2it.flipzon.model.Category;

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

    private String subcategory;
}
