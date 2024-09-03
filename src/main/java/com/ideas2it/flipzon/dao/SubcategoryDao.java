package com.ideas2it.flipzon.dao;

import java.util.List;

import com.ideas2it.flipzon.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing subcategory data from the database.
 *
 * @author Gokul
 */
@Repository
public interface SubcategoryDao extends JpaRepository<Subcategory, Long> {

    /**
     * Check subcategory name already present or not.
     *
     * @param name : name of the subcategory
     * @return boolean : true or false.
     */
    boolean existsByNameAndIsDeletedFalse(String name);

    /**
     * Find the subcategory by its id.
     *
     * @param id : id of the subcategory
     * @return Subcategory {@link Subcategory}
     */
    Subcategory findByIdAndIsDeletedFalse(long id);

    /**
     * Get the list of active subcategories.
     *
     * @return List<Subcategories> list of subcategories
     */
    List<Subcategory> findByIsDeletedFalse();
}
