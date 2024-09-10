package com.ideas2it.flipzon.controller;

import java.util.List;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.flipzon.dto.BrandDto;
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.service.BrandService;
import com.ideas2it.flipzon.service.ProductService;

/**
 * <p>
 * BrandController handles all incoming HTTP requests related to brands and return the responses
 * </p>
 *
 * @author Gokul
 */

@RestController
@RequestMapping("flipzon/api/v1/admin/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;

    /**
     * Create the Brand based on the Admin request
     *
     * @param brandDto {@link BrandDto}
     * @return BrandDto with Http status Created.
     */
    @PostMapping
    public ResponseEntity<BrandDto> addBrand(@Valid @RequestBody BrandDto brandDto) {
        return new ResponseEntity<>(brandService.addBrand(brandDto), HttpStatus.CREATED);
    }

    /**
     * Create the Brand based on the Admin request
     *
     * @param id : id of the brand
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<BrandDto> deleteBrand(@PathVariable long id) {
        brandService.deleteBrand(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get the all brands form database
     *
     * @return List<BrandDto> : list of brandDto
     */
    @GetMapping
    public ResponseEntity<List<BrandDto>> getAllBrands() {
        return new ResponseEntity<>(brandService.retrieveAllBrand(), HttpStatus.OK);
    }

    /**
     * update the Brand based on the Admin request
     *
     * @param brandDto {@link BrandDto}
     * @return BrandDto with Http status Created.
     */
    @PutMapping("/{brandId}")
    public ResponseEntity<BrandDto> updateBrand(@PathVariable Long brandId, @Valid @RequestBody BrandDto brandDto) {
        return new ResponseEntity<>(brandService.updateBrand(brandId, brandDto), HttpStatus.OK);
    }

    /**
     * update the Brand based on the Admin request
     *
     * @param id : id of the brand
     * @return BrandDto with Http status Created.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BrandDto> getBrandById(@PathVariable long id) {
        return new ResponseEntity<>(brandService.retrieveBrandById(id), HttpStatus.OK);
    }

    /**
     * Get the all products by brand id
     *
     * @return List<ProductDto> : list of productDto
     */
    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductDto>> getAllProductsByBrandId(@PathVariable Long id) {
        return new ResponseEntity<>(productService.retrieveAllProductByBrandId(id), HttpStatus.OK);
    }
}
