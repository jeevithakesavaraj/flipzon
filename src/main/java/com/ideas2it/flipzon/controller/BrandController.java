package com.ideas2it.flipzon.controller;

import com.ideas2it.flipzon.dto.BrandDto;
import com.ideas2it.flipzon.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * BrandController handles all incoming HTTP requests related to brands and return the responses
 * </p>
 *
 * @author Gokul
 */

@RestController
@RequestMapping("flipzon/api/v1/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * Create the Brand based on the Admin request
     *
     * @param brandDto {@link BrandDto}
     * @return BrandDto with Http status Created.
     */
    @PostMapping
    public ResponseEntity<BrandDto> addBrand(@RequestBody BrandDto brandDto) {
        return new ResponseEntity<>(brandService.addBrand(brandDto), HttpStatus.CREATED);
    }

    /**
     * Create the Brand based on the Admin request
     *
     * @param id : id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<BrandDto> deleteBrand(@PathVariable long id) {
        brandService.deleteBrand(id);
        return new ResponseEntity<>(HttpStatus.OK);
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
    @PutMapping
    public ResponseEntity<BrandDto> updateBrand(@RequestBody BrandDto brandDto) {
        return new ResponseEntity<>(brandService.updateBrand(brandDto), HttpStatus.OK);
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
}
