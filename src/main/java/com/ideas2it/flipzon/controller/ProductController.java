package com.ideas2it.flipzon.controller;

import java.util.List;

import com.ideas2it.flipzon.dto.*;
import jakarta.validation.Valid;
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

import com.ideas2it.flipzon.service.ProductService;
import com.ideas2it.flipzon.service.CrossSellService;
import com.ideas2it.flipzon.service.UpsellService;

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
    private UpsellService upsellService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CrossSellService crossSellService;
    
    /**
     * Create the Product based on the Admin request
     *
     * @param productDto {@link ProductDto}
     * @return ProductDto with Http status Created.
     */
    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto) {
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
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @PathVariable Long productId, @RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.updateProduct(productId,productDto), HttpStatus.OK);
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
     * @param productId product Id
     * @param productPriceDto {@link ProductPriceDto}
     * @return ProductDto with Http status OK.
     */
    @PutMapping("/{id}/price")
    public ResponseEntity<ProductDto> updateProductPrice(@Valid @PathVariable Long productId,  @RequestBody ProductPriceDto productPriceDto) {
        return new ResponseEntity<>(productService.updateProductPrice(productId, productPriceDto.getPrice()), HttpStatus.OK);
    }

    /**
     * Add cross-sell product to the product by admin request
     * @param crossSellRequestDto {@link CrossSellRequestDto}
     * @return {@link CrossSellResponseDto}
     */
    @PostMapping("/{productId}/cross-sells")
    public ResponseEntity<CrossSellResponseDto> addCrossSellProduct(@Valid @PathVariable Long productId, @RequestBody CrossSellRequestDto crossSellRequestDto) {
        return new ResponseEntity<>(crossSellService.addCrossSellProduct(productId, crossSellRequestDto), HttpStatus.OK);
    }


    /**
     * Delete cross-sell product to the product by admin request
     * @param productId id of the product
     * @param id id of the cross-sell product
     * @return Http status NoContent
     */
    @DeleteMapping("/{productId}/cross-sells/{id}")
    public ResponseEntity<CrossSellResponseDto> removeCrossSellProduct(@Valid @PathVariable Long productId, @PathVariable Long id) {
        crossSellService.removeCrossSellProduct(productId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Add upsell product to the product by admin request
     * @param upsellDto : {@link UpsellDto}
     * @return {@link UpsellResponseDto}
     */
    @PostMapping("/{productId}/upsells")
    public ResponseEntity<UpsellResponseDto> addUpsell(@Valid @PathVariable Long productId, @RequestBody UpsellDto upsellDto) {
        return ResponseEntity.ok(upsellService.addUpsell(productId, upsellDto));
    }

    /**
     * Delete cross-sell product to the product by admin request
     *
     * @return {@link UpsellResponseDto} with HTTP status OK.
     */
    @DeleteMapping("/{productId}/upsells/{id}")
    public ResponseEntity<UpsellResponseDto> deleteUpsell(@Valid @PathVariable Long productId, @PathVariable Long id) {
        return ResponseEntity.ok(upsellService.deleteUpsell(productId, id));
    }

    /**
     * Get the cross-sell products for this product id;
     * @param id : id of the product
     * @return {@link CrossSellResponseDto} with HTTP status OK.
     */
    @GetMapping("/{id}/cross-sells")
    public ResponseEntity<CrossSellResponseDto> getCrossSellProduct(@PathVariable long id) {
        return ResponseEntity.ok(crossSellService.getCrossSellProduct(id));
    }

    /**
     * Get the Up-sell products for this product id;
     * @param id : id of the product
     * @return {@link UpsellResponseDto} with HTTP status OK.
     */
    @GetMapping("/{id}/upsells")
    public ResponseEntity<UpsellResponseDto> getUpSellProduct(@PathVariable long id) {
        return ResponseEntity.ok(upsellService.getUpSellProduct(id));
    }
}