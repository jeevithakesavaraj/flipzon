package com.ideas2it.flipzon.service;

import java.util.List;

import com.ideas2it.flipzon.dto.BrandDto;

/**
 * <p>
 * BrandService defines the contract for the Brand-related operations.
 * This includes CRUD operations and additional business logic.
 * </p>
 *
 * @author Gokul
 */
public interface BrandService {

    /**
     * Create the new Brand
     *
     * @param brandDto : new brand details
     * @return BrandDto : brand details
     */
    BrandDto addBrand(BrandDto brandDto);

    /**
     * Delete the brand by its id
     *
     * @param id : id of the brand
     */
    void deleteBrand(long id);

    /**
     * view all brands
     *
     * @return List<BrandDto> : List of all brand details
     */
    List<BrandDto> retrieveAllBrand();

    /**
     * Update the Brand details by its id
     *
     * @param brandDto : new brand details
     * @return BrandDto : brand details
     */
    BrandDto updateBrand(BrandDto brandDto);

    /**
     * Get brand details by its id
     *
     * @param id : id of the brand
     */
    BrandDto retrieveBrandById(long id);
}
