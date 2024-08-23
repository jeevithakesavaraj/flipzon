package com.ideas2it.flipzon.controller;

import com.ideas2it.flipzon.dto.BrandDto;
import com.ideas2it.flipzon.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * BrandController handles all incoming HTTP requests related to brands and return the responses
 * </p>
 *
 * @author Gokul
 */

@RestController
@RequestMapping("api/v1/brands")
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
}
