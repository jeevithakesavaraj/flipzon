package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.BrandDto;
import com.ideas2it.flipzon.model.Brand;

/**
 * Object to Data Transfer Object and Dto to Object Conversion
 * @author Gokul
 */
public class BrandMapper {

    /**
     * Convert the brand to the brandDto
     * @param brand {@link Brand}
     * @return BrandDto {@link BrandDto}
     */
    public static BrandDto convertEntityToDto(Brand brand) {
        return BrandDto.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }

    /**
     * Convert the brandDto to the brand
     * @param brandDto {@link BrandDto}
     * @return Brand {@link Brand}
     */
    public static Brand convertDtoToEntity(BrandDto brandDto) {
        return Brand.builder()
                .id(brandDto.getId())
                .name(brandDto.getName())
                .build();
    }
}

