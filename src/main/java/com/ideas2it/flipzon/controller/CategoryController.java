package com.ideas2it.flipzon.controller;

import java.util.List;

import com.ideas2it.flipzon.dto.CategoryDto;
import com.ideas2it.flipzon.service.CategoryService;
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

/**
 * <p>
 * CategoryController handles all incoming HTTP requests related to category and return the responses
 * </p>
 *
 * @author Gokul
 */

@RestController
@RequestMapping("flipzon/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Create the Category based on the Admin request
     *
     * @param categoryDto {@link CategoryDto}
     * @return CategoryDto with Http status Created.
     */
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    /**
     * Create the Category based on the Admin request
     *
     * @param id : id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
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
    @PutMapping
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.updateCategory(categoryDto), HttpStatus.OK);
    }

    /**
     * update the Category based on the Admin request
     * @param id The id of the category
     * @return CategoryDto with Http status Created.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable long id) {
        return new ResponseEntity<>(categoryService.retrieveCategoryById(id), HttpStatus.OK);
    }

//    /**
//     * Create a new subcategory and associate it with a parent category.
//     * @param parentId The ID of the parent category.
//     * @param categoryDto The CategoryDto containing subcategory details.
//     * @return The created CategoryDto.
//     */
//    @PostMapping("/{parentId}/subcategories")
//    public CategoryDto createSubcategory(@PathVariable("parentId") long parentId, @RequestBody CategoryDto categoryDto) {
//        return categoryService.addSubcategory(parentId, categoryDto);
//    }

//    /**
//     * Endpoint to retrieve subcategories for a given category ID.
//     * @param id The ID of the parent category.
//     * @return A list of CategoryDto representing the subcategories.
//     */
//    @GetMapping("/{id}/subcategories")
//    public List<CategoryDto> getSubcategoriesByCategoryId(@PathVariable("id") long id) {
//        return categoryService.retrieveSubcategoriesByCategoryId(id);
//    }
}
