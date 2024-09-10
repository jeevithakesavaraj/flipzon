package com.ideas2it.flipzon.service;

import java.util.List;

import com.ideas2it.flipzon.dto.CategoryDto;

/**
 * <p>
 * CategoryService defines the contract for the Category-related operations.
 * This includes CRUD operations and additional business logic.
 * </p>
 *
 * @author Gokul
 */
public interface CategoryService {

    /**
     * Create the new Category
     *
     * @param categoryDto : new category details
     * @return CategoryDto : category details
     */
    CategoryDto addCategory(CategoryDto categoryDto);

    /**
     * Delete the category by its id
     *
     * @param id : id of the category
     * @return boolean
     */
    boolean deleteCategory(long id);

    /**
     * view all categories
     *
     * @return List<CategoryDto> : List of all category details
     */
    List<CategoryDto> retrieveAllCategory();

    /**
     * Update the Category details by its id
     * @param id category id
     * @param categoryDto : new category details
     * @return CategoryDto : category details
     */
    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    /**
     * Get category details by its id
     *
     * @param id : id of the category
     */
    CategoryDto retrieveCategoryById(long id);
}
