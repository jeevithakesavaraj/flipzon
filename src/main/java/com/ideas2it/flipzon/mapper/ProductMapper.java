package com.ideas2it.flipzon.mapper;


import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.model.Product;

/**
 * Object to Data Transfer Object and Dto to Object Conversion
 */
public class ProductMapper {

    /**
     * Convert the product to the productDto
     * @param product {@link Product}
     * @return ProductDto {@link ProductDto}
     */
    public static ProductDto convertEntityToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .brandId(product.getBrand().getName())
                .category(product.getCategory().getName())
                .category(product.getSubcategory())
                .build();
    }

    /**
     * Convert the productDto to the product
     * @param productDto {@link ProductDto}
     * @return Product {@link Product}
     */
    public static Product convertDtoToEntity(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .build();
    }
}

