package com.ideas2it.flipzon.service;

import java.util.List;

import com.ideas2it.flipzon.dto.SubcategoryDto;

/**
 * <p>
 * SubcategoryService defines the contract for the Subcategory-related operations.
 * This includes CRUD operations and additional business logic.
 * </p>
 *
 * @author Gokul
 */
public interface SubcategoryService {

    /**
     * Create the new Subcategory
     *
     * @param subcategoryDto : new subcategory details
     * @return SubcategoryDto : subcategory details
     */
    SubcategoryDto addSubcategory(SubcategoryDto subcategoryDto);

    /**
     * Delete the subcategory by its id
     *
     * @param id : id of the subcategory
     * @return boolean
     */
    boolean deleteSubcategory(long id);

    /**
     * view all subcategories
     *
     * @return List<SubcategoryDto> : List of all subcategory details
     */
    List<SubcategoryDto> retrieveAllSubcategory();

    /**
     * Update the Subcategory details by its id
     *
     * @param subcategoryDto : new subcategory details
     * @return SubcategoryDto : subcategory details
     */
    SubcategoryDto updateSubcategory(SubcategoryDto subcategoryDto);

    /**
     * Get subcategory details by its id
     *
     * @param id : id of the subcategory
     */
    SubcategoryDto retrieveSubcategoryById(long id);
}
