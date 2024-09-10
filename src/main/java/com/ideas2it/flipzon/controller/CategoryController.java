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

import com.ideas2it.flipzon.dto.CategoryDto;
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.service.CategoryService;
import com.ideas2it.flipzon.service.ProductService;

/**
 * <p>
 * CategoryController handles all incoming HTTP requests related to category and return the responses
 * </p>
 *
 * @author Gokul
 */

@RestController
@RequestMapping("flipzon/api/v1/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    /**
     * Create the Category based on the Admin request
     *
     * @param categoryDto {@link CategoryDto}
     * @return CategoryDto with Http status Created.
     */
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    /**
     * Create the Category based on the Admin request
     *
     * @param id : Category id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get the all categories form database
     *
     * @return List<CategoryDto> : list of categoryDto
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        return new ResponseEntity<>(categoryService.retrieveAllCategory(), HttpStatus.OK);
    }

    /**
     * update the Category based on the Admin request
     *
     * @param categoryDto {@link CategoryDto}
     * @return CategoryDto with Http status Created.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.updateCategory(id, categoryDto), HttpStatus.OK);
    }

    /**
     * update the Category based on the Admin request
     * @param id : category id
     * @return CategoryDto with Http status Created.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable long id) {
        return new ResponseEntity<>(categoryService.retrieveCategoryById(id), HttpStatus.OK);
    }

    /**
     * Get the all products by category id
     *
     * @return List<ProductDto> : list of productDto
     */
    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategoryId(@PathVariable Long id) {
        return new ResponseEntity<>(productService.retrieveAllProductByCategoryId(id), HttpStatus.OK);
    }
}
