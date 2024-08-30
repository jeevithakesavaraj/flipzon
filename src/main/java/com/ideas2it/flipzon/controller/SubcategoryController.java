package com.ideas2it.flipzon.controller;

import java.util.List;

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

import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.dto.SubcategoryDto;
import com.ideas2it.flipzon.service.ProductService;
import com.ideas2it.flipzon.service.SubcategoryService;

/**
 * <p>
 * SubcategoryController handles all incoming HTTP requests related to subcategory and return the responses
 * </p>
 *
 * @author Gokul
 */

@RestController
@RequestMapping("flipzon/api/v1/admin/subcategories")
public class SubcategoryController {

    @Autowired
    private SubcategoryService subcategoryService;
    @Autowired
    private ProductService productService;

    /**
     * Create the Subcategory based on the Admin request
     *
     * @param subcategoryDto {@link SubcategoryDto}
     * @return SubcategoryDto with Http status Created.
     */
    @PostMapping
    public ResponseEntity<SubcategoryDto> addSubcategory(@RequestBody SubcategoryDto subcategoryDto) {
        return new ResponseEntity<>(subcategoryService.addSubcategory(subcategoryDto), HttpStatus.CREATED);
    }

    /**
     * Create the Subcategory based on the Admin request
     *
     * @param id : id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<SubcategoryDto> deleteSubcategory(@PathVariable long id) {
        subcategoryService.deleteSubcategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get the all Subcategories form database
     *
     * @return List<SubcategoryDto> : list of subcategoryDto
     */
    @GetMapping
    public ResponseEntity<List<SubcategoryDto>> getAllSubcategory() {
        return new ResponseEntity<>(subcategoryService.retrieveAllSubcategory(), HttpStatus.OK);
    }

    /**
     * update the Subcategory based on the Admin request
     *
     * @param subcategoryDto {@link SubcategoryDto}
     * @return SubcategoryDto with Http status Created.
     */
    @PutMapping
    public ResponseEntity<SubcategoryDto> updateSubcategory(@RequestBody SubcategoryDto subcategoryDto) {
        return new ResponseEntity<>(subcategoryService.updateSubcategory(subcategoryDto), HttpStatus.OK);
    }

    /**
     * update the Subcategory based on the Admin request
     * @param id The id of the subcategory
     * @return SubcategoryDto with Http status Created.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SubcategoryDto> getSubcategoryById(@PathVariable long id) {
        return new ResponseEntity<>(subcategoryService.retrieveSubcategoryById(id), HttpStatus.OK);
    }

    /**
     * Get the all products by category id
     *
     * @return List<ProductDto> : list of productDto
     */
    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductDto>> getAllProductBySubcategoryId(@PathVariable Long id) {
        return new ResponseEntity<>(productService.retrieveAllProductBySubcategoryId(id), HttpStatus.OK);
    }
}
