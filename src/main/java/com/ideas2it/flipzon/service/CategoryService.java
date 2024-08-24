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

//    /**
//     * Create a new subcategory and associate it with a parent category.
//     * @param parentId The ID of the parent category.
//     * @param categoryDto The CategoryDto containing subcategory details.
//     * @return The created CategoryDto.
//     */
//    CategoryDto addSubcategory(long parentId, CategoryDto categoryDto);

    /**
     * Delete the category by its id
     *
     * @param id : id of the category
     */
    void deleteCategory(long id);

    /**
     * view all categorys
     *
     * @return List<CategoryDto> : List of all category details
     */
    List<CategoryDto> retrieveAllCategory();

    /**
     * Update the Category details by its id
     *
     * @param categoryDto : new category details
     * @return CategoryDto : category details
     */
    CategoryDto updateCategory(CategoryDto categoryDto);

    /**
     * Get category details by its id
     *
     * @param id : id of the category
     */
    CategoryDto retrieveCategoryById(long id);

//    /**
//     * Get Subcategory details by category id
//     *
//     * @param id : id of the category
//     */
//    List<CategoryDto> retrieveSubcategoriesByCategoryId(long id);
}
