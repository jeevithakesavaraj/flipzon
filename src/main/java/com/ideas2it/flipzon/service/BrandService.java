package com.ideas2it.flipzon.service;

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
     * Create the new department
     *
     * @param brandDto : new brand details
     * @return BrandDto : brand details
     */
    BrandDto addBrand(BrandDto brandDto);
}
