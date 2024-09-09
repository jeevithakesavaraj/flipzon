package com.ideas2it.flipzon.service;

import java.util.List;

import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.model.Product;

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
     * @return ProductDto : {@link ProductDto}
     */
    ProductDto addProduct(ProductDto productDto);

    /**
     * Delete the product by its id
     *
     * @param id : id of the product
     *
     * @return boolean
     */
    boolean deleteProduct(long id);

    /**
     * view all products
     *
     * @return List<ProductDto> : List of all product details
     */
    List<ProductDto> retrieveAllProduct();

    /**
     * Update the Product details by its id
     * @param productId id of the product
     * @param productDto : new product details
     * @return ProductDto : product details
     */
    ProductDto updateProduct(Long productId, ProductDto productDto);
    
    /**
     * Update the Product price details by its id
     *
     * @param productDto : {@link ProductDto}
     * @return ProductDto : product details
     */
    ProductDto updateProductPrice(Long productId, ProductDto productDto);

    /**
     * Get product details by its id with check stock is available or not
     *
     * @param id : id of the product
     */
    ProductDto retrieveProductById(Long id);

    /**
     * Get product details by its id
     *
     * @param id : id of the product
     */
    Product getProductById(Long id);
    /**
     * Get All product details by brand id
     *
     * @param id : id of the brand
     * @return List<ProductDto> : List of {@link ProductDto}
     */
    List<ProductDto>retrieveAllProductByBrandId(Long id);

    /**
     * Get All product details by Category id
     *
     * @param id : id of the Category
     * @return List<ProductDto> : List of {@link ProductDto}
     */
    List<ProductDto>retrieveAllProductByCategoryId(Long id);

    /**
     * Get All product details by Subcategory id
     *
     * @param id : id of the Subcategory
     * @return List<ProductDto> : List of {@link ProductDto}
     */
    List<ProductDto>retrieveAllProductBySubcategoryId(Long id);
}
