package com.ideas2it.flipzon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.flipzon.model.Category;

/**
 * Repository interface for accessing category data from the database.
 *
 * @author Gokul
 */
@Repository
public interface CategoryDao extends JpaRepository<Category, Long> {

    /**
     * Check the category name is present or not.
     * @param name : name of the category
     * @return true or false.
     */
    boolean existsByNameAndIsDeletedFalse(String name);

    /**
     * Find the categroy by its id
     * @param id : id of the category
     * @return Category {@link Category}
     */
    Category findByIdAndIsDeletedFalse(long id);

    /**
     * Get all categories in Database
     * @return List<Category>
     */
    List<Category> findByIsDeletedFalse();
}
