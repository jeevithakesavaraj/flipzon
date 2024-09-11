package com.ideas2it.flipzon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.service.ProductService;

@RestController
@RequestMapping("flipzon/api/v1/public")
public class UserController {
    @Autowired
    private ProductService productService;

    /**
     * <p>
     *  Get the list of products
     * </p>
     *
     * @return List<ProductDto> {@link ProductDto}
     */
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return new ResponseEntity<>(productService.retrieveAllProduct(), HttpStatus.OK);
    }

    /**
     * <p>
     * Get products by brand Id
     * </p>
     * @param id : id of the brand
     * @return List<ProductDto> {@link ProductDto}
     */
    @GetMapping("/brands/{brandId}/products")
    public ResponseEntity<List<ProductDto>> getAllProductsByBrandId(@PathVariable Long id) {
        return new ResponseEntity<>(productService.retrieveAllProductByBrandId(id), HttpStatus.OK);
    }

    /**
     * <p>
     * Get products by category
     * </p>
     * @param categoryId : id of the category
     * @return List<ProductDto> {@link ProductDto}
     */
    @GetMapping("/categories/{categoryId}/products")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategoryId(@PathVariable Long categoryId) {
        return new ResponseEntity<>(productService.retrieveAllProductByCategoryId(categoryId), HttpStatus.OK);
    }

    /**
     * <p>
     * Get products by category
     * </p>
     * @param subCategoryId : id of the sub category
     * @return List<ProductDto> {@link ProductDto}
     */
    @GetMapping("/subcategories/{subCategoryId}/products")
    public ResponseEntity<List<ProductDto>> getAllProductsBySubcategoryId(@PathVariable Long subCategoryId) {
        return new ResponseEntity<>(productService.retrieveAllProductBySubcategoryId(subCategoryId), HttpStatus.OK);
    }

    /**
     * <p>
     * Filter products by brand name, category name, subcategory name
     * </p>
     * @param categoryName : category name
     * @param brandName : brand name
     * @param subcategoryName : subcategory name
     * @return List<ProductDto> {@link ProductDto}
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProductByFilter(@RequestParam(required = false) String categoryName,
                                                                  @RequestParam(required = false) String subcategoryName,
                                                                  @RequestParam(required = false) String brandName) {
        return new ResponseEntity<>(productService.searchProductByFilter(categoryName, subcategoryName, brandName), HttpStatus.OK);
    }
}
