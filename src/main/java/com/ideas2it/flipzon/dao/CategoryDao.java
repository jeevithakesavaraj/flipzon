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

    boolean existsByNameAndIsDeletedFalse(String name);

    Category findByIdAndIsDeletedFalse(long id);

    List<Category> findByIsDeletedFalse();
}
