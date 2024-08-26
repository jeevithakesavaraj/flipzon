package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.CategoryDto;
import com.ideas2it.flipzon.model.Category;

/**
 * Object to Data Transfer Object and Dto to Object Conversion
 * @author Gokul
 */
public class CategoryMapper {

    /**
     * Convert the category to the categoryDto
     * @param category {@link Category}
     * @return CategoryDto {@link CategoryDto}
     */
    public static CategoryDto convertEntityToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .subcategory(category.getSubcategory())
                .build();
    }

    /**
     * Convert the categoryDto to the category
     * @param categoryDto {@link CategoryDto}
     * @return Category {@link Category}
     */
    public static Category convertDtoToEntity(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .subcategory(categoryDto.getSubcategory())
                .build();
    }
}

