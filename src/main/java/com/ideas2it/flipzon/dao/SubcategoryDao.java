package com.ideas2it.flipzon.dao;

import java.util.List;

import com.ideas2it.flipzon.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.flipzon.model.Category;

/**
 * Repository interface for accessing subcategory data from the database.
 *
 * @author Gokul
 */
@Repository
public interface SubcategoryDao extends JpaRepository<Subcategory, Long> {

    boolean existsByNameAndIsDeletedFalse(String name);

    Subcategory findByIdAndIsDeletedFalse(long id);

    List<Subcategory> findByIsDeletedFalse();
}
