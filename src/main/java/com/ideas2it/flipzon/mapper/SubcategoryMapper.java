package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.SubcategoryDto;
import com.ideas2it.flipzon.model.Subcategory;

/**
 * Object to Data Transfer Object and Dto to Object Conversion
 * @author Gokul
 */
public class SubcategoryMapper {

    /**
     * Convert the subcategory to the subcategoryDto
     * @param subcategory {@link Subcategory}
     * @return SubcategoryDto {@link SubcategoryDto}
     */
    public static SubcategoryDto convertEntityToDto(Subcategory subcategory) {
        return SubcategoryDto.builder()
                .id(subcategory.getId())
                .name(subcategory.getName())
                .categoryId(subcategory.getCategory().getId())
                .build();
    }

    /**
     * Convert the subcategoryDto to the subcategory
     * @param subcategoryDto {@link SubcategoryDto}
     * @return Subcategory {@link Subcategory}
     */
    public static Subcategory convertDtoToEntity(SubcategoryDto subcategoryDto) {
        return Subcategory.builder()
                .id(subcategoryDto.getId())
                .name(subcategoryDto.getName())
                .build();
    }
}

