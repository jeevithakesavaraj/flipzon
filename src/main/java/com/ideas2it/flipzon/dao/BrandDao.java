package com.ideas2it.flipzon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.flipzon.model.Brand;
/**
 * Repository interface for accessing brand data from the database.
 *
 * @author Gokul
 */
@Repository
public interface BrandDao extends JpaRepository<Brand, Long> {

    /**
     * Check brand name already present or not.
     *
     * @param name : name of the brand
     * @return boolean : true or false.
     */
    boolean existsByNameAndIsDeletedFalse(String name);

    /**
     * Find the brand by its id.
     *
     * @param id : id of the brand
     * @return Brand {@link Brand}
     */
    Brand findByIdAndIsDeletedFalse(long id);

    /**
     * Get the list of active brands.
     *
     * @return List<Brand> list of brands
     */
    List<Brand> findByIsDeletedFalse();
}
