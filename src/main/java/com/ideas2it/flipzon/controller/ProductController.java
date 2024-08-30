package com.ideas2it.flipzon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.service.ProductService;

/**
 * <p>
 * ProductController handles all incoming HTTP requests related to products and return the responses
 * </p>
 *
 * @author Gokul
 */

@RestController
@RequestMapping("flipzon/api/v1/admin/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Create the Product based on the Admin request
     *
     * @param productDto {@link ProductDto}
     * @return ProductDto with Http status Created.
     */
    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.addProduct(productDto), HttpStatus.CREATED);
    }

    /**
     * Create the Product based on the Admin request
     *
     * @param id : id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get the all products form database
     *
     * @return List<ProductDto> : list of productDto
     */
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return new ResponseEntity<>(productService.retrieveAllProduct(), HttpStatus.OK);
    }

    /**
     * update the Product based on the Admin request
     *
     * @param productDto {@link ProductDto}
     * @return ProductDto with Http status Created.
     */
    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.updateProduct(productDto), HttpStatus.OK);
    }

    /**
     * Get the Product based on the Admin request
     *
     * @param id : id of the product
     * @return ProductDto with Http status Created.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable long id) {
        return new ResponseEntity<>(productService.retrieveProductById(id), HttpStatus.OK);
    }

    /**
     * update the Product price based on the Admin request
     *
     * @return ProductDto with Http status Created.
     */
    @PatchMapping
    public ResponseEntity<ProductDto> updateProductPrice(@RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.updateProductPrice(productDto), HttpStatus.OK);
    }
}
