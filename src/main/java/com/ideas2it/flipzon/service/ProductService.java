package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dto.ProductDto;

import java.util.List;

/**
 * <p>
 * ProductService defines the contract for the Product-related operations.
 * This includes CRUD operations and additional business logic.
 * </p>
 *
 * @author Gokul
 */
public interface ProductService {

    /**
     * Create the new Product
     *
     * @param productDto : new product details
     * @return ProductDto : product details
     */
    ProductDto addProduct(ProductDto productDto);

    /**
     * Delete the product by its id
     *
     * @param id : id of the product
     */
    void deleteProduct(long id);

    /**
     * view all products
     *
     * @return List<ProductDto> : List of all product details
     */
    List<ProductDto> retrieveAllProduct();

    /**
     * Update the Product details by its id
     *
     * @param productDto : new product details
     * @return ProductDto : product details
     */
    ProductDto updateProduct(ProductDto productDto);

    /**
     * Get product details by its id
     *
     * @param id : id of the product
     */
    ProductDto retrieveProductById(long id);
}
